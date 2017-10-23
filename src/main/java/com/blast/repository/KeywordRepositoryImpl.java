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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;

import com.blast.domain.FeedItem;
import com.blast.domain.Keyword;
import com.blast.service.dto.TrendStatusDTO;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;


/**
 * Spring Data JPA repository for the BrandkeyProduct entity.
 */
@SuppressWarnings("unused")
public class KeywordRepositoryImpl implements KeywordRepositoryExtend {

	@PersistenceContext
    private EntityManager entityManager;
	
	private JpaEntityInformation<FeedItem, ?> entityInformation;
	
	@PostConstruct
    public void postConstruct() {
        this.entityInformation = JpaEntityInformationSupport.getEntityInformation(FeedItem.class, entityManager);
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

	@Override
	public Page<Keyword> findTopKeywordOfFriend(List<Long> lstUserId, Integer status, Pageable pageable) {
		String sqlCount = "select count(*) from ("
						  + "   select DISTINCT kw.name, number_like from feed_item fi"
						  + "       INNER JOIN keyword kw on fi.main_keyword = kw.name"
						  + "       where fi.user_id in (" + StringUtils.join(lstUserId, ",") + ") and status = :status) AS item";
		
		Query queryTotal = entityManager.createNativeQuery(sqlCount);
		queryTotal.setParameter("status", status);
		BigInteger countResultBig = (BigInteger) queryTotal.getSingleResult();
		long countResult = countResultBig.longValue();
			
		// Like
		String sqlQuery = "";
		if (status == 1) {
			sqlQuery = "select DISTINCT kw.name, number_like from feed_item fi"
					+ "   INNER JOIN keyword kw on fi.main_keyword = kw.name"
					+ "    where fi.user_id in (" + StringUtils.join(lstUserId, ",") + ") and status = :status"
					+ "    order by number_like desc";
		} else {
			sqlQuery = "select DISTINCT kw.name, number_hate from feed_item fi"
					+ "   INNER JOIN keyword kw on fi.main_keyword = kw.name"
					+ "    where fi.user_id in (" + StringUtils.join(lstUserId, ",") + ") and status = :status"
					+ "    order by number_hate desc";
		}
		
		Query query = entityManager.createNativeQuery(sqlQuery);
		query.setParameter("status", status);
		query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize()); 
		query.setMaxResults(pageable.getPageSize());
		
        List data = query.getResultList();
        List<Keyword> keywordData = new ArrayList<>();
        if (data != null) {
        	for (Object obj : data) {
        		Object[] tmp = (Object[]) obj;
        		String keyword = (String) tmp[0];
        		Keyword itemKeyword = findByName(keyword);
        		keywordData.add(itemKeyword);
			}
        	
        }
        
        Page<Keyword> page = new PageImpl<>(keywordData, pageable, countResult);
        return page;
	}
	
	private Keyword findByName(String name) {
		String sqlQuery = "select * from keyword where name = :name";
		Query query = entityManager.createNativeQuery(sqlQuery, Keyword.class);
		query.setParameter("name", name);
		Keyword data = (Keyword) query.getSingleResult();
		return data;
	}

	@Override
	public Page<Keyword> findTopKeywordOfAll(Integer status, Pageable pageable) {
		String sqlCount = "select count(*) from keyword";

		Query queryTotal = entityManager.createNativeQuery(sqlCount);
		BigInteger countResultBig = (BigInteger) queryTotal.getSingleResult();
		long countResult = countResultBig.longValue();
			
		// Like
		String sqlQuery = "";
		if (status == 1) {
			sqlQuery = "select * from keyword where number_like > 0 order by number_like desc";
		} else {
			sqlQuery = "select * from keyword where number_hate > 0 order by number_hate desc";
		}
		
		Query query = entityManager.createNativeQuery(sqlQuery, Keyword.class);
		query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize()); 
		query.setMaxResults(pageable.getPageSize());
		
		List<Keyword> data = query.getResultList();
		
		Page<Keyword> page = new PageImpl<>(data, pageable, countResult);
		return page;
	}

	@Override
	public List<Long> findLatestUserIdByKeyword(Integer number, Integer status, String name) {
		List<Long> lstUserId = new ArrayList<>();
		
		String sqlQuery =  "select DISTINCT user_id from feed_item where main_keyword = :keyword and status = :status";
		
		Query query = entityManager.createNativeQuery(sqlQuery);
		query.setParameter("keyword", name);
		query.setParameter("status", status);
		query.setFirstResult(0); 
		query.setMaxResults(number);
		
		List data = query.getResultList();
		if (data != null) {
        	for (Object obj : data) {
        		BigInteger tmp = (BigInteger) obj;
        		lstUserId.add(tmp.longValue());
			}
        	
        }
		return lstUserId;
	}
}
