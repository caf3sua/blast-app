package com.blast.repository;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;

import com.blast.domain.FeedItem;
import com.blast.service.dto.TrendStatusDTO;


/**
 * Spring Data JPA repository for the BrandkeyProduct entity.
 */
@SuppressWarnings("unused")
public class StatusItemRepositoryImpl implements StatusItemRepositoryExtend {

	@PersistenceContext
    private EntityManager entityManager;
	
	private JpaEntityInformation<FeedItem, ?> entityInformation;
	
	@PostConstruct
    public void postConstruct() {
        this.entityInformation = JpaEntityInformationSupport.getEntityInformation(FeedItem.class, entityManager);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public List<TrendStatusDTO> countByItemIdAndCreatedDateRanger(Long userId, String fromDate, String toDate) {
		// select created_date, count(*), status from status_item where user_id = 1253 GROUP BY created_date, status order by created_date
				Query query = entityManager.createNativeQuery("select created_date date, count(*) number, status from status_item "
						+ " where item_id in (select id from feed_item where user_id = :user_id) "
						+ " and created_date between :from_date and :to_date "
						+ " GROUP BY created_date, status"
						+ " order by created_date");
				query.setParameter("user_id", userId);
				query.setParameter("from_date", string2Date(fromDate));
				query.setParameter("to_date", string2Date(toDate));
				
		        List data = query.getResultList();
		        return parseResult(data);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<TrendStatusDTO> countByUserIdAndCreatedDateRanger(Long userId, String fromDate, String toDate) {
		// select created_date, count(*), status from status_item where user_id = 1253 GROUP BY created_date, status order by created_date
		Query query = entityManager.createNativeQuery("select created_date date, count(*) number, status from status_item where user_id = :user_id "
				+ " and created_date between :from_date and :to_date "
				+ " GROUP BY created_date, status"
				+ " order by created_date");
		query.setParameter("user_id", userId);
		query.setParameter("from_date", string2Date(fromDate));
		query.setParameter("to_date", string2Date(toDate));
		
        List data = query.getResultList();
        return parseResult(data);
	}
	
	@SuppressWarnings("rawtypes")
	private List<TrendStatusDTO> parseResult(List data) {
		List<TrendStatusDTO> result = new ArrayList<>();
		if (data != null) {
        	for (Object obj : data) {
        		Object[] tmp = (Object[]) obj;
        		TrendStatusDTO trend = new TrendStatusDTO();
        		trend.setDate((Date)tmp[0]);
        		int statusTmp = (Integer)tmp[2];
        		if (statusTmp == 0) {
        			// hate
        			trend.setNumberHate(((BigInteger)tmp[1]).longValue());
        		} else {
        			trend.setNumberLike(((BigInteger)tmp[1]).longValue());
        		}
        		result.add(trend);
			}
        	
        }
		return result;
	}
	
	private Date string2Date(String yyyyMMdd) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        try {

            Date date = formatter.parse(yyyyMMdd);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return null;
	}

//	@Override
//	public Page<FeedItemDTO> findAllByUserId(Long userId, Pageable pageable) {
////		public Page<Device> findAllWithGridView(Pageable pageable) {
//			Query query = new Query();
//			query.with(pageable);
//			query.fields().include("_id");
//			query.fields().include("model");
//			query.fields().include("abbrName");
//			query.fields().include("countryCode");
//			
//			List<Device> lstDevices = mongoTemplate.find(query, Device.class);
//			long total = mongoTemplate.count(query, Device.class);
//			Page<Device> devicePage = new PageImpl<Device>(lstDevices, pageable, total);
//
//			return devicePage;
////		}
//		
//			entityManager.
//		// TODO Auto-generated method stub
//		return null;
//	}
	
}
