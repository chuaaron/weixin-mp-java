/**
 * 
 */
package org.hamster.weixinmp.dao.entity.base;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hamster.weixinmp.config.WxConfig;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author grossopaforever@gmail.com
 * @version Jul 28, 2013
 * 
 */

@Entity
@Table(name = WxConfig.TABLE_PREFIX + "msg_info")
@DiscriminatorColumn(name = "msg_info", length = 20)
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class WxBaseMsgInfoEntity extends WxBaseEntity {
	@Column(name = "appid", length = WxConfig.COL_LEN_CONTENT, nullable = true)
	protected String appid;
	@Column(name = "create_time", nullable = true)
	protected Long createTime;
	@Column(name = "info_type", length = WxConfig.COL_LEN_INDICATOR, nullable = true)
	protected String infoType;
}
