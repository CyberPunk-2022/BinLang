FROM node:16-alpine


#使用阿里云的镜像源
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories

#添加作者 个人地址: https://gitee.com/io123
MAINTAINER csdn 我来秋风落叶 shisheng@live.com

#设置时区
ENV TZ=Asia/Shanghai

#git仓库下载 配置成你自己的git地址
#RUN git clone https://gitee.com/io123/mobile-ui.git /app

# 设置工作目录
WORKDIR /app

# 将当前目录下的所有文件复制到容器中的/app目录下
COPY . /app

#删除node_modules文件夹
RUN rm -rf node_modules
# 使用Yarn安装项目依赖
RUN npm install

# 构建Vite项目
RUN npm run build

# 设置容器启动时执行的命令，使用Yarn运行Vite项目
#CMD ["yarn", "run", "dev"]

#拉取一个nginx
RUN apk add nginx

RUN mkdir /usr/share/nginx/html
#将服务内部的/app/dist目录挂载到nginx的html目录
RUN cp -r /app/dist/* /usr/share/nginx/html


#生产需要改一下这个打包保存的目录,避免测试环境跟生成环境造成冲突
COPY nginx/nginx.conf /etc/nginx/nginx.conf

#删除app文件夹所有文件,避免磁盘浪费
RUN rm -rf /app


#启动nginx

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]