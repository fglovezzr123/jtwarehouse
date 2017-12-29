package com.tojoycloud.frame.report.entity;

public class AppInfo
{
	// 产品ID
		private String product_id;

		// 产品版本号
		private String version_code;

		// 访问渠道
		private String visit_device;

		public String getProduct_id()
		{
			return product_id;
		}

		public void setProduct_id(String product_id)
		{
			this.product_id = product_id;
		}

		public String getVersion_code()
		{
			return version_code;
		}

		public void setVersion_code(String version_code)
		{
			this.version_code = version_code;
		}

		public String getVisit_device()
		{
			return visit_device;
		}

		public void setVisit_device(String visit_device)
		{
			this.visit_device = visit_device;
		}
}
