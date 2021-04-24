## tinymce
此包包含 tinymce 的各类插件，包括 plugins 和 skins 和 langs。此包的 plugins 和 skins 文件夹是非必要的，因为 tinymce.min.js 里已经生成好了，这里保留的目的是描述 tinymce.min.js 加载了哪些拓展包。
+ plugins 和 skins 的生成  
可在 [https://www.tiny.cloud/get-tiny/custom-builds/](https://www.tiny.cloud/get-tiny/custom-builds/) 自定义生成，生成后的包包含了 tinymce 和选择的插件。
+ langs  
tinymce 在初始化的时候如果设置了非 en 语言的配置，则会在 tinymce.min.js 文件的地址的同级目录的 langs 文件夹去加载对应的语言包，这里添加了中文的支持，如果想添加别的语言，可在 [https://www.tiny.cloud/get-tiny/language-packages/](https://www.tiny.cloud/get-tiny/language-packages/) 里下载。