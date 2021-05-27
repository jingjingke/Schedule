## Schedule日程记录（androidd原生APP）
通过 APP 可以新增、修改、查询日程记录，并且实时更新用时信息。
### 效果图
下面截屏了几张效果，初始做的比较简单。

<img src="https://jingjingke.github.io/Schedule/demo/1.png" alt="效果截图1" width="240px" /> <img src="https://jingjingke.github.io/Schedule/demo/2.png" alt="效果截图2" width="240px" /> <img src="https://jingjingke.github.io/Schedule/demo/4.png" alt="效果截图3" width="240px" />
<img src="https://jingjingke.github.io/Schedule/demo/3.png" alt="效果截图4" width="240px" /> <img src="https://jingjingke.github.io/Schedule/demo/5.png" alt="效果截图5" width="240px" /> <img src="https://jingjingke.github.io/Schedule/demo/6.png" alt="效果截图6" width="240px" />

### 数据库中表的设计
使用SQLite轻型数据库，只设计了3张表：status、schedule、progress。

status 表有以下4种状态，在数据库初始化时会创建该表并将值写入。

|  id   | name  |
|  :----:  | :----:  |
|  1  | 未开始  |
|  2  | 进行中  |
|  3  | 已暂停  |
|  4  | 已完成  |

schedule 表除了主键ID外，主要字段：日程名称、内容、创建备注、关联的状态ID、创建时间、耗时。当我们创建/修改日程时，主要往该表新增/修改数据。并且我们可以在列表页，通过 status_id 对数据进行状态筛选。

|  id   | name  |  content   | remark  |  status_id   | create_time  | cost_time  |
|  :----:  | :----:  |  :----:  | :----:  |  :----:  | :----:  | :----:  |
|  ...  | ...  |  ...  | ...  |  ...  | ...  | ...  |   

progress 表除了主键ID外，主要字段：关联的日程ID、进度的备注、一段进程的开始与结束时间。当我们点击开始、暂停、完成按钮时，主要往该表添加数据，同时当一段日程暂停/完成时，会计算耗时并将结果更新到 schedule 表中。

|  id   | schedule_id  |  remark   | start_time  |  end_time   |
|  :----:  | :----:  |  :----:  | :----:  |  :----:  |
|  ...  | ...  |  ...  | ...  |  ...  |


> 参考数据库中表的设计，代码中主要有两个类：Schedule、Progress，类中的属性与表基本一致。


