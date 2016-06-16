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
@Table(name = "wx_query_authorizer_auth")
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class WxQueryAuthorizerAuth extends WxBaseEntity {
	@SerializedName("authorizer_info")
	private WxAuthorizerInfo authorizerInfo;
	@SerializedName("authorization_info")
	private WxAuthorizationInfo authorizationInfo;	
}
