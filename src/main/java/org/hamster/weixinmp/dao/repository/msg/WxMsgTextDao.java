/**
 * 
 */
package org.hamster.weixinmp.dao.repository.msg;

import java.util.List;

import org.hamster.weixinmp.dao.entity.msg.WxMsgTextEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author grossopaforever@gmail.com
 * @version Jul 28, 2013
 * 
 */
@Repository
public interface WxMsgTextDao extends
		PagingAndSortingRepository<WxMsgTextEntity, Long> {
	List<WxMsgTextEntity> findByOpenId(String userId, String agentId);
}
