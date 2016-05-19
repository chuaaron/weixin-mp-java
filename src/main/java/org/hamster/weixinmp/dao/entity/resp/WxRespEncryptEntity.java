/**
 * 
 */
package org.hamster.weixinmp.dao.entity.resp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hamster.weixinmp.config.WxConfig;
import org.hamster.weixinmp.dao.entity.base.WxBaseRespEntity;


/**
 * @author grossopaforever@gmail.com
 * @version Jul 28, 2013
 * 
 */
@Entity
@Table(name = WxConfig.TABLE_PREFIX + "resp_encrypt")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WxRespEncryptEntity extends WxBaseRespEntity {
	@Column(name = "appid", length = WxConfig.COL_LEN_CONTENT, nullable = true)
	private String appid;
	@Column(name = "encrypt", length = WxConfig.COL_LEN_CONTENT, nullable = true)
	private String encrypt;
}
