/**
 * 
 */
package org.hamster.weixinmp.dao.entity.msg;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hamster.weixinmp.config.WxConfig;
import org.hamster.weixinmp.constant.WxMsgType;
import org.hamster.weixinmp.dao.entity.base.WxBaseMsgEntity;

/**
 * @author grossopaforever@gmail.com
 * @version Jul 28, 2013
 * 
 * 
 */
@Entity
@Table(name = WxConfig.TABLE_PREFIX + "msg_encrypt")
@DiscriminatorValue(WxMsgType.ENCRYPT)
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WxMsgEncryptEntity extends WxBaseMsgEntity {
	@Column(name = "appid", length = WxConfig.COL_LEN_CONTENT, nullable = true)
	private String appid;
	@Column(name = "encrypt", length = WxConfig.COL_LEN_CONTENT, nullable = true)
	private String encrypt;
}
