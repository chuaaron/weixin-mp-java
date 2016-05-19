/**
 * 
 */
package org.hamster.weixinmp.dao.repository.auth;

import org.hamster.weixinmp.dao.entity.auth.WxComponentAuth;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author grossopaforever@gmail.com
 * @version Aug 4, 2013
 *
 */
@Repository
public interface WxComponentAuthDao extends PagingAndSortingRepository<WxComponentAuth, Long> {

}
