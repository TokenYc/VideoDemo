# VideoDemo
### 类似于微信的小视频录制demo
主要解决的问题：
 - Android原生预览录制只支持特定的分辨率，demo可以自定义比例，如1:1 demo使用的4:3
 - 视频过大问题 使用ffmpeg对视频进行裁剪和压缩

### 用到的东西
 - Camera 用于预览
 - MediaRecorder 用于录像
 - MediaPlayer 用于播放
 - ffmpeg 视频压缩和裁剪
 
### 存在的问题
 - 视频压缩时间过长，通常6s的视频压缩需要3s-4s 暂时还没找到解决方案
