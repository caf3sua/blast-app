package com.blast.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.blast.service.dto.ItemInfoDTO;
import com.blast.service.shoppingapi.dto.AmazonImageInfo;
import com.blast.service.shoppingapi.dto.AmazonOfferDTO;
import com.blast.service.shoppingapi.dto.SimilarProductDTO;
import com.blast.service.util.SignedRequestsHelper;
import com.blast.web.rest.vm.AdviceVM;

@Service
public class AmazonProductApiService {

	private final Logger log = LoggerFactory.getLogger(AmazonProductApiService.class);

	/*
	 * Your Access Key ID, as taken from the Your Account page.
	 */
	@Value("${spring.amazon.webservice.product-api.access-key}")
	private String AWS_ACCESS_KEY_ID;

	/*
	 * Your Secret Key corresponding to the above ID, as taken from the Your
	 * Account page.
	 */
	@Value("${spring.amazon.webservice.product-api.secrect-key}")
	private String AWS_SECRET_KEY;

	@Value("${spring.amazon.webservice.product-api.associate-tag}")
	private String ASSOCIATE_TAG;

	/*
	 * Use the end-point according to the region you are interested in.
	 */
	private static final String ENDPOINT = "webservices.amazon.com";

	@PostConstruct
	private void init() {
		try {

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public AmazonProductApiService() {
	}

	public List<ItemInfoDTO> itemSearch(AdviceVM adviceVM) {
		List<ItemInfoDTO> result = null;
		/*
		 * Set up the signed requests helper
		 */
		SignedRequestsHelper helper;
		try {
			helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}

		String requestUrl = null;

		/*
		 * The helper can sign requests in two forms - map form and string form
		 */

		/*
		 * Here is an example in map form, where the request parameters are
		 * stored in a map.
		 */
		System.out.println("Map form example:");
		Map<String, String> params = new HashMap<String, String>();
		
		// ItemPage
		String itemPage = "5";
		if (StringUtils.isNotEmpty(adviceVM.getItemPage())) {
			itemPage = adviceVM.getItemPage();
		}

		params.put("Service", "AWSECommerceService");
		params.put("Operation", "ItemSearch");
		params.put("AWSAccessKeyId", AWS_ACCESS_KEY_ID);
		params.put("AssociateTag", ASSOCIATE_TAG);
		params.put("SearchIndex", "All");
		params.put("ResponseGroup", "EditorialReview,Images,Offers,Variations,ItemAttributes,Similarities");
		params.put("Keywords", adviceVM.getKeyword());
		params.put("ItemPage", itemPage);
		

		requestUrl = helper.sign(params);
		System.out.println("Signed Request is \"" + requestUrl + "\"");

		result = fetchItemData(requestUrl);

		return result;
	}

	/*
	 * Utility function to fetch the response from the service and extract the
	 * title from the XML.
	 */
	private static List<ItemInfoDTO> fetchItemData(String requestUrl) {
		List<ItemInfoDTO> result = new ArrayList<>();
		try {
			System.out.println("Reuest Product API: " + requestUrl);
			String content = org.apache.commons.io.IOUtils.toString(new URL(requestUrl), "utf8");
			System.out.println(content);

			SAXBuilder builder = new SAXBuilder();

			try {

				Document document = (Document) builder.build(new InputSource(new StringReader(content)));
				Element rootNode = document.getRootElement();
				Namespace amazonNamespace = rootNode.getNamespace();
				List list = rootNode.getChildren("Items", amazonNamespace); 
				Element itemsEle = (Element) list.get(0);

				List<Element> lstItem = itemsEle.getChildren("Item", amazonNamespace);
				lstItem.size();
				for (Element item : lstItem) {
					ItemInfoDTO itemDTO = new ItemInfoDTO();
					AmazonOfferDTO offerDTO = null;
					AmazonImageInfo sImg = null;
					AmazonImageInfo mImg = null;
					AmazonImageInfo lImg = null;
					String description = null;
					List<SimilarProductDTO> lstSimilarProductDTO = new ArrayList<>();
				
					
					String ASIN = item.getChildText("ASIN", amazonNamespace);
					String DetailPageURL = item.getChildText("DetailPageURL", amazonNamespace);
					if (item.getChildren("SmallImage", amazonNamespace) != null && item.getChildren("SmallImage", amazonNamespace).size() > 0) {
						Element smallImage = (Element) item.getChildren("SmallImage", amazonNamespace).get(0);
						sImg = new AmazonImageInfo(smallImage.getChildText("URL", amazonNamespace)
								, Integer.valueOf(smallImage.getChildText("Height", amazonNamespace))
								, Integer.valueOf(smallImage.getChildText("Width", amazonNamespace)));
						System.out.println(sImg);
					}
					
					if (item.getChildren("MediumImage", amazonNamespace) != null && item.getChildren("MediumImage", amazonNamespace).size() > 0) {
						Element mediumImage = (Element) item.getChildren("MediumImage", amazonNamespace).get(0);
						 mImg = new AmazonImageInfo(mediumImage.getChildText("URL", amazonNamespace)
								, Integer.valueOf(mediumImage.getChildText("Height", amazonNamespace))
								, Integer.valueOf(mediumImage.getChildText("Width", amazonNamespace)));
						System.out.println(mImg);
					}
					
					if (item.getChildren("LargeImage", amazonNamespace) != null && item.getChildren("LargeImage", amazonNamespace).size() > 0) {
						Element largeImage = (Element) item.getChildren("LargeImage", amazonNamespace).get(0);
						lImg = new AmazonImageInfo(largeImage.getChildText("URL", amazonNamespace)
								, Integer.valueOf(largeImage.getChildText("Height", amazonNamespace))
								, Integer.valueOf(largeImage.getChildText("Width", amazonNamespace)));
						System.out.println(largeImage);
					}
					
					if (item.getChildren("EditorialReviews", amazonNamespace) != null && item.getChildren("EditorialReviews", amazonNamespace).size() > 0) {
						Element editorialReviews = (Element) item.getChildren("EditorialReviews", amazonNamespace).get(0);
						Element editorialReview = (Element) editorialReviews.getChildren("EditorialReview", amazonNamespace).get(0);

						description = editorialReview.getChildText("Content", amazonNamespace);
					}
					
					if (item.getChildren("SimilarProducts", amazonNamespace) != null && item.getChildren("SimilarProducts", amazonNamespace).size() > 0) {
						Element similarProducts = (Element) item.getChildren("SimilarProducts", amazonNamespace).get(0);
						List<Element> lstSimilarProducts = similarProducts.getChildren("SimilarProduct", amazonNamespace);
						for (Element ele : lstSimilarProducts) {
							String asin = ele.getChildText("ASIN", amazonNamespace);
							String title = ele.getChildText("Title", amazonNamespace);
							SimilarProductDTO e = new SimilarProductDTO();
							e.setAsin(asin);
							e.setTitle(title);
							lstSimilarProductDTO.add(e);
						}
					}
					
					// OfferSummary
					String amount = null;
					String currencyCode = null;
					String formattedPrice = null;
					if (item.getChildren("OfferSummary", amazonNamespace) != null && item.getChildren("OfferSummary", amazonNamespace).size() > 0) {
						Element offerEle = (Element) item.getChildren("OfferSummary", amazonNamespace).get(0);
						if (offerEle.getChildren("LowestNewPrice", amazonNamespace) != null && offerEle.getChildren("LowestNewPrice", amazonNamespace).size() > 0) {
							Element priceEle = (Element) offerEle.getChildren("LowestNewPrice", amazonNamespace).get(0);
							amount = priceEle.getChildText("Amount", amazonNamespace);
							currencyCode = priceEle.getChildText("CurrencyCode", amazonNamespace);
							formattedPrice = priceEle.getChildText("FormattedPrice", amazonNamespace);
							
							offerDTO = new AmazonOfferDTO();
							offerDTO.setAmount(Long.valueOf(amount));
							offerDTO.setCurrencyCode(currencyCode);
							offerDTO.setFormattedPrice(formattedPrice);
						}
					}
					
					// ItemAttributes
					String title = null;
					String brand = null;
					if (item.getChildren("ItemAttributes", amazonNamespace) != null && item.getChildren("ItemAttributes", amazonNamespace).size() > 0) {
						Element attEle = (Element) item.getChildren("ItemAttributes", amazonNamespace).get(0);
						brand = attEle.getChildText("Brand", amazonNamespace);
						title = attEle.getChildText("Title", amazonNamespace);
					}
					
					// Set data
					itemDTO.setTitle(title);
					itemDTO.setBrand(brand);
					itemDTO.setSmallImage(sImg);
					itemDTO.setMediumImage(mImg);
					itemDTO.setLargeImage(lImg);
					itemDTO.setDescription(description);
					itemDTO.setAsin(ASIN);
					itemDTO.setDetailPageURL(DetailPageURL);
					itemDTO.setOffer(offerDTO);
					itemDTO.setSimilarProducts(lstSimilarProductDTO);
					
					result.add(itemDTO);
				}
				
			} catch (IOException io) {
				System.out.println(io.getMessage());
			} catch (JDOMException jdomex) {
				System.out.println(jdomex.getMessage());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return result;
	}

}
