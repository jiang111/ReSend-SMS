# ReSend-SMS
  建议使用163邮箱作为发件人，QQ邮箱发件太频繁需要验证

    将手机接收到的短信以邮件的形式发送至指定邮箱。
    
### 参数配置
    
>* 1. 请将你的邮箱开启smtp服务(具体开启方法请在 google 百度一下 163 为smtp.163.com QQ 为smtp.qq.com)
>* 2. 由于代码中发送邮件开启了SSL加密，端口一般默认为465
>* 3. 用户名一般为邮箱地址的名字去掉@后面几位(比如: 123#163.com 则用户名为123)
>* 4. 部分邮箱开启smtp服务之后使用的验证密码并非邮件密码，而是授权码，请注意
>* 5. 如果读取不到短信，注意授权相关的权限

### 注意事项
>* 1. 邮件频繁发送的话，有的邮箱会自动归入垃圾箱内，如果长时间未收到提醒可以到垃圾箱找找
>* 2. 请将APP加入手机白名单，让它常驻后台，这样才能准确接收到相关的短信，并发送
>* 3. 该APP不会上传用户任何信息，可通过源码查看

### 下载
    [点我下载](https://github.com/jiang111/ReSend-SMS/blob/master/release/app-release_v1.0.apk)

### License

    Copyright 2016 NewTab

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
