# SecondHandsMasket
二手交易系统app
**********************************************
com.min.second_hand包下的类

（1）AboutmeFragment         ***********
 	关于我的Fragment，其中包括
	1.未登录状态
	 （1）在此界面进行操作都会跳转到登录界面，要求进行登录
	 （2）不显示退出当前账号的按钮选项
	2.已登录状态，可以进行以下操作
	 （1）显示用户的头像，可以进行头像更改，包括从本地相册中获取或直接现拍
 	 （2）显示当前用户的用户名
 	 （3）发布帖子
 	 （4）查看该用户已发帖子
 	 （5）修改密码
 	 （6）退出当前账号，点击按钮后退出当前账号

（2）ActivityCollector
活动管理器，可以随时随地退出程序

（3）HttpUtil
	与服务器交互类。使用HttpURLConnection与服务器连接并发送HTTP请求和数据，并得到服务器返回来的数据。
sendHttpRequest方法接收一个参数，调用时需要传递一个String类型的对象，其中包括发送HTTP请求和数据

（4）Person 
用户信息封装类,包括用户id、用户名、用户密码、联系方式

（5）PostInfomation
封装帖子（商品）信息类，包括商品名称、商品类别、商品价钱、发帖用户名、发帖用户联系方式、发布时间

*****************************************
com.min.adapter包下的类
(1)PostInfoAdapter
实现帖子信息LIstView的适配器，包括配置其属性，以及每个item中删除按钮的监听事件处理并及时更新ListView


*****************************************
com.min.activity包下的类

（1）BaseActivity
 	定义一个BaseActivity继承于Activity，让每一个活动都继承BaseActivity，为的是方便管理,让每个
继承BaseActivity的Activity都在创建的时候添加到ActivityCollector的activities（一个List）中，
在摧毁时都能从activities中移除掉
     
(2)ChangePasswordActivity           ************
更改用户密码界面,要求用户输入原有密码、新密码、确认密码等信息。先进行输入判断，再进行信息合法性判断，
 如果输入合法，在提交给服务器进行数据更改

（3）DetailInfoActivity
	 每个已发帖子的详细信息界面，用户可以进行查看每一个已发帖子的详细信息，并且可以进行一定数据的修改，
 包括商品名称、商品描述、商品价钱、发布帖子的用户名、用户联系方式、发布帖子的时间。
 修改后可以像数据库提交已进行修改的信息。

（4）HavePostActivity
	显示已发帖子的列表界面，选择“已发帖子”控件时，就跳转到此界面，以列表的形式显示该用户已经发布过的帖子信息，用户可以选择点击某一个已发帖子跳转到详细界面并进行操作,
 其中包括查看、修改每个出售商品的详细信息，还可以在此界面选择删除按钮对已发帖子进行删除操作。

（5）LoginActivity
用户登录界面,通过SharedPreferences中的数据先判断之前是否已经标记登录过，如果有则显示在界面上，
登录时先判断是否选择记住密码，登陆成功时就将信息保存并跳转到主界面

(6)PublishPostActivity

(7)RegisteredActivity
注册界面，要求输入用户名，密码，确认密码，邮箱，联系方式等信息，再进行判断是否合法并进行详细的操作
