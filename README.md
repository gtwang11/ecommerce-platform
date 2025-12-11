学号：202330452951  
姓名：王国涛
### 📂 代码结构说明

本项目基于 Spring Boot 标准目录结构开发，主要代码文件及其功能说明如下：

#### 1. 后端核心逻辑 (`src/main/java/com/example/ecommerceplatform`)

* **config (配置层)**
    * `SecurityConfig.java`: 集成 Spring Security，配置 URL 访问权限（如 `/admin` 仅限管理员访问）及登录拦截规则。
    * `DataInitializer.java`: 系统启动引导类，负责自动初始化数据库，创建默认管理员账号 (`admin`) 和测试商品数据。

* **controller (控制层)**
    * `AdminController.java`: 处理管理员请求，包括后台仪表盘数据显示、商品的增删改查操作。
    * `ShopController.java`: 处理前台顾客请求，如浏览首页、加入购物车、查看订单历史。
    * `AuthController.java`: 处理用户注册、登录页面跳转逻辑。

* **service (业务逻辑层)**
    * `OrderService.java`: **核心业务类**。实现了下单流程的事务控制，确保“创建订单”与“扣减库存”原子性执行，防止超卖。
    * `ProductService.java`: 封装商品管理逻辑，实现了“级联删除保护”，防止删除已有订单关联的商品。
    * `CartService.java`: 处理购物车的添加商品、数量调整及清空操作。
    * `UserService.java`: 处理用户注册时的密码加密存储及身份认证。

* **entity (实体层)**
    * `User.java`, `Product.java`: 数据库核心表的映射实体。
    * `Order.java`, `OrderItem.java`: 订单头与订单明细实体，通过 JPA 建立一对多关联。
    * `Cart.java`, `CartItem.java`: 购物车及其条目实体。

* **repository (持久层)**
    * `ProductRepository.java`, `OrderRepository.java` 等：继承 `JpaRepository`，提供对数据库的底层 CRUD 接口。

#### 2. 前端视图资源 (`src/main/resources`)

* **templates (Thymeleaf 页面模板)**
    * **公共页面**:
        * `index.html`: 商城首页，根据用户角色动态展示“登录”或“管理员后台”按钮。
        * `login.html` / `register.html`: 用户登录与注册表单。
    * **顾客功能**:
        * `cart.html`: 购物车页面，支持动态修改数量与结算。
        * `order_history.html`: 用户的历史订单列表及详情展示。
    * **管理员后台**:
        * `admin/dashboard.html`: 后台仪表盘，展示销售额、订单数等统计图表。
        * `admin/products.html`: 商品管理列表，提供编辑和删除入口。
        * `admin/product_form.html`: 商品新增/编辑的通用表单。

* **配置文件**
    * `application.yml`: 项目核心配置，包含数据库连接池、JPA 设置及服务器端口配置。

#### 3. 部署与构建文件 (根目录)

* `Dockerfile`: 定义项目的容器化构建规则，基于 OpenJDK 17 镜像构建应用容器。
* `docker-compose.yml`: 容器编排文件，定义了 `app` (Java后端) 和 `mysqldb` (MySQL数据库) 的依赖关系、网络连接及数据卷挂载，实现一键部署。
* `pom.xml`: Maven 依赖管理文件，声明了 Spring Boot, Thymeleaf, MySQL Driver 等项目依赖。
