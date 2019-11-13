在线项目文档
https://note.youdao.com/ynoteshare1/index.html?id=f185e367693d6a0d4acc195807d10da5&type=note
<h2>shopping-mall</h2>
<p>shopping是一个面向校园的二手商城交易系统,本系统用Java编写,
该系统的意义在于可以为同学提供一个二手交易平台
同学们可以将自己闲置的物品通过这个平台出售,可以更好的方便
同学们的生活.</p>

###接口演示
####1.导出Excel
![Image text](/src/main/resources/static/image/readMe/excel.gif)
####2.加入购物车

####3.注册

####4.搜索


##组成
<h4>1.shopping-mall-admin  后台管理系统</h4>
该系统主要是管理shopping-mall商城和shopping-mall店铺系统,扮演的角色是管理员<br>
1.审核店铺提交的商品信息<br>
2.管理用户信息,例如:用户有违规的操作,要禁用该用户<br>
3.管理订单信息 例如:订单发货,订单退货<br>

<h4>2.shopping-mall  二手校园交易系统</h4>
该系统主要是为同学提供交易的平台,方便同学生活<br>
主要功能:<br>
1.购买商品<br>
2.商品加入购物车<br>
3.结算商品清单<br>
4.支付商品<br>

<h4>3.shopping-mall  店铺管理系统</h4>
每个同学既是买家的同时又是卖家,不仅可以购买商品还可以出售商品
在注册的同时为每个同学提供店铺<br>
主要功能:<br>
1.管理自己的商品<br>
2.如果交易成功,要发货的功能<br>


##项目演示
前台演示:<a href="http://49.235.206.214">shopping-mall Demo</a><br>
后台管理:<a href="http://49.235.206.214">shopping-mall-admin Demo</a>
