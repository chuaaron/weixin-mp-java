/**
 * 
 */
package org.hamster.weixinmp.dao.entity.auth;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hamster.weixinmp.dao.entity.base.WxBaseEntity;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * 
 * @author grossopaforever@gmail.com
 * @version Aug 3, 2013
 * 
 */
@Entity
@Table(name = "wx_authorizer_info")
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class WxAuthorizationBusinessInfo extends WxBaseEntity{
	@Column(name = "open_pay", nullable = false)
	private Integer openPay;		
	@Column(name = "open_shake", nullable = false)
	private Integer openShake;
	@Column(name = "open_scan", nullable = false)
	private Integer openScan;
	@Column(name = "open_card", nullable = false)
	private Integer openCard;
	@Column(name = "open_store", nullable = false)
	private Integer openStore;	
}

