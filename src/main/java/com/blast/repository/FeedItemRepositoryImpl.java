package com.blast.repository;

import java.math.BigInteger;
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
public class FeedItemRepositoryImpl implements FeedItemRepositoryExtend {

	@PersistenceContext
    private EntityManager entityManager;
	
	private JpaEntityInformation<FeedItem, ?> entityInformation;
	
	@PostConstruct
    public void postConstruct() {
        this.entityInformation = JpaEntityInformationSupport.getEntityInformation(FeedItem.class, entityManager);
    }

	@Override
	public Page<FeedItem> findByUserIdAndStatus(Long userId, Integer status, Pageable pageable) {
		Query queryTotal = entityManager.createNativeQuery
			    ("select count(*) from feed_item "
				+ " where id in (select item_id from status_item where user_id = :user_id and status = :status)");
		queryTotal.setParameter("user_id", userId);
		queryTotal.setParameter("status", status);
		BigInteger countResultBig = (BigInteger) queryTotal.getSingleResult();
		long countResult = countResultBig.longValue();
			
		Query query = entityManager.createNativeQuery("select * from feed_item "
				+ " where id in (select item_id from status_item where user_id = :user_id and status = :status) "
				+ " order by id desc", FeedItem.class);
		query.setParameter("user_id", userId);
		query.setParameter("status", status);
		query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize()); 
		query.setMaxResults(pageable.getPageSize());
		
        List<FeedItem> data = query.getResultList();
        
        Page<FeedItem> page = new PageImpl<>(data, pageable, countResult);
        return page;
	}

	@Override
	public Page<FeedItem> findAllByTrendFriendAndStatus(List<Long> friendId, Integer status, Pageable pageable) {
		String sqlCount = "select count(*) from status_item"
				+ " where user_id in (" + StringUtils.join(friendId, ",") + ")"
				+ " and status = :status";
		Query queryTotal = entityManager.createNativeQuery
			    (sqlCount);
		queryTotal.setParameter("status", status);
		BigInteger countResultBig = (BigInteger) queryTotal.getSingleResult();
		long countResult = countResultBig.longValue();
		
		String sql = "select count(*) number, item_id from status_item"
				+ " where user_id in (" + StringUtils.join(friendId, ",") + ")"
				+ " and status = :status"
				+ " GROUP BY item_id"
				+ " order by number desc";
		Query query = entityManager.createNativeQuery(sql);
		//query.setParameter("user_id", userId);
		query.setParameter("status", status);
		query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize()); 
		query.setMaxResults(pageable.getPageSize());
		
        List data = query.getResultList();
        
        // Loop
        List<FeedItem> result = new ArrayList<>();
        if (data != null) {
        	for (Object obj : data) {
        		Object[] tmp = (Object[]) obj;
        		long statusTmp = ((BigInteger)tmp[1]).longValue();
        		FeedItem item = entityManager.find(FeedItem.class, statusTmp);
        		result.add(item);
			}
        }
        
        Page<FeedItem> page = new PageImpl<>(result, pageable, countResult);
        return page;
	}

	@Override
	public Page<FeedItem> findAllByTrendAllAndStatus(Integer status, Pageable pageable) {
		String sqlCount = "select count(*) from status_item"
				+ " where status = :status";
		Query queryTotal = entityManager.createNativeQuery(sqlCount);
		queryTotal.setParameter("status", status);
		BigInteger countResultBig = (BigInteger) queryTotal.getSingleResult();
		long countResult = countResultBig.longValue();
		
		String sql = "select count(*) number, item_id from status_item"
				+ " where status = :status"
				+ " GROUP BY item_id"
				+ " order by number desc";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("status", status);
		query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize()); 
		query.setMaxResults(pageable.getPageSize());
		
        List data = query.getResultList();
        
        // Loop
        List<FeedItem> result = new ArrayList<>();
        if (data != null) {
        	for (Object obj : data) {
        		Object[] tmp = (Object[]) obj;
        		long statusTmp = ((BigInteger)tmp[1]).longValue();
        		FeedItem item = entityManager.find(FeedItem.class, statusTmp);
        		result.add(item);
			}
        }
        
        Page<FeedItem> page = new PageImpl<>(result, pageable, countResult);
        return page;
	}

	@Override
	public FeedItem findFirstByMainKeywordFriend(List<Long> lstUserId, String mainKeyword) {
		String sqlQuery = "select fi.* from feed_item fi"
					+ "   INNER JOIN keyword kw on fi.main_keyword = kw.name"
					+ "    where fi.user_id in (" + StringUtils.join(lstUserId, ",") + ")"
					+ "    and kw.name = :keyword"
					+ "    order by fi.id desc";
		
		Query query = entityManager.createNativeQuery(sqlQuery, FeedItem.class);
		query.setParameter("keyword", mainKeyword);
		query.setFirstResult(0); 
		query.setMaxResults(1);
		
        FeedItem data = (FeedItem) query.getSingleResult();// getResultList();
        return data;
	}
	
}
