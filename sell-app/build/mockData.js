//启动api server  
var express = require('express')
var apiServer = express();  

//引入body-parser 模块  
var bodyParser = require("body-parser");  
apiServer.use(bodyParser.urlencoded({extended:true}));  
apiServer.use(bodyParser.json());  

//定义api router  
var apiRouter = express.Router();  
var fs = require("fs");  
apiRouter.route("/:apiName")  
.all(function(req,res){  
    //读db.json 中的数据作为接口返回数据  
    fs.readFile('./data.json',function(err,data){  
        if(err) throw err;  
        var data = JSON.parse(data);  
        if(data[req.params.apiName]){  
            res.json(data[req.params.apiName]);  
        }else{  
            res.send('no such api name');  
        }  
    })  
})  

//监听服务地址  
apiServer.use('/api',apiRouter);  
apiServer.listen(8081,function(err){  
    if(err){  
        console.log(err);  
        return;  
    }  
     console.log("listening at http://localhost:" + 8081 + "\n");  
  
})  