开源地址：http://code.taobao.org/svn/CSEProject/trunk/CSEIII/
目录结构摘要：
src->data里为已爬取计算的nba本地数据及图片信息
src->main->java里为主要的工程源代码
包括：blService  bl逻辑接口
      bl 逻辑层
      data  数据处理层
      dataForSql  数据库初始化包
      newui UI界面层
      SQLHelper  数据库操作包
      vo  封装的数据结构包
      web 网络爬虫基本包



部署：下载源码导入工程后

step1:安装mysql,设置root密码为12345a,或修改src->main->java->SQLHelper-			>SqlManager.java里的String userPasswd为自己的密码
      
step2：运行初始化类:src->main-<java->dataForSql->Init.java


step3:运行主界面入口,src->main->java->newui->mainui->MainFrame.java
     