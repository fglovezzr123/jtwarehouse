function Scroll( id,direction,length,speed)
{
    var _this = this;
    this.timer = null;
    var oDiv = document.getElementById(id);
    this.oUl = oDiv.getElementsByTagName("ul")[0];

    //this.oli = this.oUl.getElementsByTagName("li");

    clearInterval(this.timer);
    if(direction =="right"){
        this.oUl.style.left = -length+"px";
    }
    if(direction =="left"){
        this.oUl.style.left = -length+"px";
    }
    if(direction =="top"){
        this.oUl.style.top = -length+"px";
    }
    if(direction =="bottom"){
        this.oUl.style.top = -length+"px";
    }


    this.timer =  setInterval(function()
    {
        if(direction =="right"){
            _this.TimerRightFn(speed,length);
        }
        if(direction =="left"){
            _this.TimerLeftFn(speed,length);
        }
        if(direction =="top"){
            _this.TimerTopFn(speed,length);
        }
        if(direction =="bottom"){
            _this.TimerBottomFn(speed,length);
        }
    },30);

    oDiv.onmousemove = function()
    {
        window.clearInterval(_this.timer);
    }

    oDiv.onmouseout = function()
    {
        window.clearInterval(_this.timer);

        _this.timer =  setInterval(function()
        {
            if(direction =="right"){
                _this.TimerRightFn(speed,length);
            }
            if(direction =="left"){
                _this.TimerLeftFn(speed,length);
            }
            if(direction =="top"){
                _this.TimerTopFn(speed,length);
            }
            if(direction =="bottom"){
                _this.TimerBottomFn(speed,length);
            }
        },30);
    }

};
Scroll.prototype.TimerBottomFn =function (speed,length)
{
//向下滚


    if(parseInt(this.oUl.style.top) >=0)
    {
        this.oUl.style.top = -length+"px";
    }
    this.oUl.style.top =parseInt(this.oUl.style.top) +speed+"px";
};
Scroll.prototype.TimerTopFn =function (speed,length)
{
    //向上滚
    if(parseInt(this.oUl.style.top) < -length)
    {
        this.oUl.style.top = 0+"px";
    }
    this.oUl.style.top =parseInt(this.oUl.style.top) -speed+"px";
};
Scroll.prototype.TimerLeftFn =function (speed,length)
{
    //向左滚
    if(parseInt(this.oUl.style.left) <=-length)
    {
        this.oUl.style.left = 0+"px";
    }
    this.oUl.style.left =parseInt(this.oUl.style.left) -speed+"px";
};
Scroll.prototype.TimerRightFn =function (speed,length)
{
    //向右滚
    if(parseInt(this.oUl.style.left) >=0)
    {
        this.oUl.style.left = 0-length+"px";
    }
    this.oUl.style.left =parseInt(this.oUl.style.left) + speed +"px";
}
