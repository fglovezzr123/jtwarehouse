/*!
 *  @copyright (c) 2016
 *  @author: wenfeng.lei
 *  @update: Tue Mar 14 2017 20:17:13 GMT+0800 (中国标准时间)
 */
!
function(e) {
    function t(n) {
        if (i[n]) return i[n].exports;
        var r = i[n] = {
            exports: {},
            id: n,
            loaded: !1
        };
        return e[n].call(r.exports, r, r.exports, t),
        r.loaded = !0,
        r.exports
    }
    var i = {};
    return t.m = e,
    t.c = i,
    t.p = "/",
    t(0)
} ([function(e, t, i) {
    function n(e, t) {
        return c.sign ? t ? $.trim(t.text) ? t.text && $.trim(t.text).length > 140 ? o.trigger(e, {
            code: 10003,
            msg: l[10003]
        }) && !1 : ("1" == s.getter("is_gag") && o.trigger(e, {
            code: 10002,
            msg: l[10002]
        }) && !1, 1 != s.getter("forbidchat") || o.trigger(e, {
            code: 10004,
            msg: l[10004]
        }) && !1) : o.trigger(e, {
            code: 10001,
            msg: l[10001]
        }) && !1 : o.trigger(e, {
            code: 1e4,
            msg: l[1e4]
        }) && !1 : o.trigger(e, {
            code: 1006,
            msg: "sign error"
        }) && !1
    }
    var r = i(15),
    o = i(1),
    a = i(2),
    s = i(4),
    c = i(11),
    d = i(8),
    u = i(3),
    l = i(10),
    p = i(7);
    window.VHALL_SDK = window.VHALL_SDK || {},
    VHALL_SDK.Version = "2.0.0",
    VHALL_SDK.on = o.listen,
    VHALL_SDK.remove = o.remove,
    VHALL_SDK.init = r,
    VHALL_SDK.getRoominfo = a.get,
    VHALL_SDK.getUserinfo = s.get,
    VHALL_SDK.vhall_get_live_history_chat_msg = function() {
        var e = Array.prototype.slice.call(arguments);
        c.getLiveChatMsg(e)
    },
    VHALL_SDK.vhall_get_live_history_question_msg = function() {
        var e = Array.prototype.slice.call(arguments);
        c.getQuestionlist(e)
    },
    VHALL_SDK.vhall_get_record_history_chat_msg = function() {
        var e = Array.prototype.slice.call(arguments);
        c.getRecordChatMsg(e)
    },
    VHALL_SDK.sendChat = function(e) {
        if (!n("sendChat", e)) return ! 1;
        var t = s.get();
        return "1" != t.is_gag && 1 != t.forbidchat && (1 == VHALL_SDK.getRoominfo().type ? d.sendChat(e) : c.sendRecordChat({
            content: e.text,
            webinar_id: VHALL_SDK.options.roomid,
            nick_name: t.username,
            user_id: t.userid,
            avatar: t.avatar
        })),
        {
            avatar: t.avatar,
            content: u(e.text),
            user_name: t.username,
            user_id: t.userid,
            role: t.role
        }
    },
    VHALL_SDK.sendQuestion = function(e) {
        if (!n("sendQuestion", e)) return ! 1;
        if (1 != VHALL_SDK.getRoominfo().type) return o.trigger("sendQuestion", {
            code: 10005,
            msg: l[10005]
        }) && !1;
        if (1 != VHALL_SDK.getRoominfo().openQuestion) return o.trigger("sendQuestion", {
            code: 10006,
            msg: l[10006]
        }) && !1;
        var t = s.get();
        "1" != t.is_gag && 1 != t.forbidchat && d.sendQuestion(e.text);
        var i = new Date;
        return i = (i.getHours() > 9 ? i.getHours() : "0" + i.getHours()) + ":" + (i.getMinutes() > 9 ? i.getMinutes() : "0" + i.getMinutes()),
        {
            avatar: t.avatar,
            data: {
                content: u(e.text),
                created_at: i,
                join_id: t.userid,
                nick_name: t.username
            }
        }
    },
    VHALL_SDK.player = p
},
function(e, t) {
    var i = {},
    n = null,
    r = null,
    o = null;
    n = function(e, t) {
        i[e] || (i[e] = []),
        i[e].push(t)
    },
    r = function() {
        var e = Array.prototype.slice.call(arguments),
        t = Array.prototype.shift.call(e),
        n = i[t];
        if (n && n.length >= 0) for (var r = 0,
        o = n.length; r < o; r++) n[r].apply(null, e)
    },
    o = function(e, t) {
        var n = i[e];
        if (!n) return ! 1;
        if (t) for (var r = n.length - 1; r >= 0; r--) {
            var o = n[r];
            o === t && n.splice(r, 1)
        } else n && (n.length = 0)
    },
    e.exports = {
        listen: n,
        trigger: r,
        remove: o
    }
},
function(e, t, i) {
    var n = i(9),
    r = {};
    e.exports = {
        create: function(e) {
            n(e) && (r = e)
        },
        setter: function(e, t) {
            r[e] = t
        },
        getter: function(e) {
            return r[e]
        },
        get: function() {
            return r
        }
    }
},
function(e, t, i) {
    function n(e) {
        if (e) {
            e = e.replace(/\</g, "&lt;").replace(/\>/g, "&gt;").replace(/\n/g, "<br/>");
            var t = "//cnstatic01.e.vhall.com/static/img/arclist/",
            i = e.match(/\[[^@]{1,3}\]/g);
            if (null !== i) for (var n = 0; n < i.length; n++) {
                var r = o(i[n]);
                r && (e = e.replace(i[n], '<img width="24" src="' + t + "Expression_" + r + '@2x.png" border="0"/>'))
            }
        }
        return e
    }
    i(25);
    var r = i(5),
    o = i(23);
    $.fn.qqFace = function(e) {
        var t = {
            id: "facebox",
            path: "face/",
            assign: "#content"
        },
        i = $.extend(t, e),
        n = i.assign,
        a = i.id,
        s = i.path;
        return n.length <= 0 ? (alert("缺少表情赋值对象。"), !1) : ($(this).click(function(e) {
            var t, n, c;
            if (!$(this).hasClass("disabled")) {
                var d = $(this).parent().find("." + a);
                if (d.length <= 0) if (r) {
                    t = '<div class="' + a + ' facebox-pc mCustomScrollbar"><table border="0" cellspacing="0" cellpadding="0"><tr>';
                    for (var u = 1; u <= 90; u++) n = o(u - 1),
                    t += '<td><img width="24" src="' + s + "Expression_" + u + '@2x.png" onclick="$(\'' + i.assign + "').setCaret();$('" + i.assign + "').insertAtCaret('" + n + "');\" /></td>",
                    u % 9 === 0 && (t += "</tr><tr>");
                    t += "</tr></table></div>",
                    $(this).parent().append(t),
                    c = $(this).position()
                } else {
                    var l = $(window).width(),
                    p = "";
                    t = '<div class="' + a + ' facebox-mobile" style="width:' + l + 'px;"><div class="qqFace-box" style="width:' + 5 * l + 'px">';
                    for (var u = 1; u <= 5; u++) {
                        t += '<div class="qqFace-mobile" style="width:' + l + 'px">';
                        for (var g = 1; g <= 20; g++) {
                            var f = 20 * (u - 1) + g;
                            n = o(f - 1),
                            n && (t += "<li onclick=\"$('" + i.assign + "').setCaret();$('" + i.assign + "').insertAtCaret('" + n + '\');"><img width="24" src="' + s + "Expression_" + f + '@2x.png" /></li>')
                        }
                        t += "<li onclick=\"$('" + i.assign + '\').deleteCaret();" ><img width="24" src="' + s + 'faceDelete@2x.png" /></li></div>',
                        p += 1 === u ? "<a class='active'></a>": "<a></a>"
                    }
                    t += "<div style='clear:both'></div></div><div class='text-center'>" + p + "</div></div>",
                    $(this).parent().append(t),
                    c = $(this).position(),
                    d = $(this).parent().find("." + a),
                    d.data("data", {
                        index: 0
                    });
                    var h, v, m = !1,
                    y = d.find(".qqFace-box").eq(0).get(0);
                    d[0].addEventListener("touchstart",
                    function(e) {
                        h = e.touches[0].pageX
                    },
                    !1),
                    d[0].addEventListener("touchmove",
                    function(e) {
                        e.preventDefault(),
                        v = e.touches[0].pageX - h;
                        var t = $(this).data("data").index * l,
                        i = "translate3d(" + (v - t) + "px, 0, 0)";
                        y.style.webkitTransform = i,
                        y.style.mozTransform = i,
                        y.style.transform = i,
                        m = !0
                    },
                    !1),
                    d[0].addEventListener("touchend",
                    function(e) {
                        if (m) {
                            var t = $(this).data("data").index,
                            i = t * l;
                            v < -50 ? t < 4 && (t += 1, $(this).data("data", {
                                index: t
                            }), i += l) : v > 50 && t >= 1 && (t -= 1, $(this).data("data", {
                                index: t
                            }), i -= l),
                            d.find(".text-center a").removeClass("active").eq(t).addClass("active");
                            var n = "translate3d(-" + i + "px, 0, 0)";
                            y.style.webkitTransform = n,
                            y.style.mozTransform = n,
                            y.style.transform = n
                        }
                        m = !1
                    },
                    !1),
                    d.on("click", "li",
                    function(e) {
                        e.stopPropagation()
                    })
                }
                $(this).parent().find("." + a).toggle(),
                e.stopPropagation()
            }
        }), void $(document).click(function() {
            $("." + a).hide()
        }))
    },
    $.fn.extend({
        selectContents: function() {
            $(this).each(function(e) {
                var t, i, n, r, o = this; (n = o.ownerDocument) && (r = n.defaultView) && "undefined" != typeof r.getSelection && "undefined" != typeof n.createRange && (t = window.getSelection()) && "undefined" != typeof t.removeAllRanges ? (i = n.createRange(), i.selectNode(o), 0 === e && t.removeAllRanges(), t.addRange(i)) : document.body && "undefined" != typeof document.body.createTextRange && (i = document.body.createTextRange()) && (i.moveToElementText(o), i.select())
            })
        },
        setCaret: function() {
            if (/msie/.test(navigator.userAgent.toLowerCase())) {
                var e = function() {
                    var e = $(this).get(0);
                    e.caretPos = document.selection.createRange().duplicate()
                };
                $(this).click(e).select(e).keyup(e)
            }
        },
        insertAtCaret: function(e) {
            var t = $(this).get(0);
            if (document.all && t.createTextRange && t.caretPos) {
                var i = t.caretPos;
                i.text = "" === i.text.charAt(i.text.length - 1) ? e + "": e
            } else if (t.setSelectionRange) {
                var n = t.selectionStart,
                o = t.selectionEnd,
                a = t.value.substring(0, n),
                s = t.value.substring(o);
                t.value = a + e + s;
                var c = e.length;
                t.setSelectionRange(n + c, n + c),
                r ? $(this).focus() : $(this).blur()
            } else t.value += e
        },
        deleteCaret: function() {
            var e = $(this),
            t = e.val(),
            i = /(\[[^@]{1,3}\])$/;
            t = i.test(t) ? t.replace(i, "") : t.substring(0, t.length - 1),
            e.val(t),
            e.blur()
        }
    }),
    e.exports = n
},
function(e, t, i) {
    var n = i(9),
    r = {};
    e.exports = {
        create: function(e) {
            n(e) && (r = e)
        },
        setter: function(e, t) {
            r[e] = t
        },
        getter: function(e) {
            return r[e]
        },
        get: function() {
            return r
        }
    }
},
function(e, t) {
    function i() {
        for (var e = navigator.userAgent,
        t = ["Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"], i = !0, n = 0; n < t.length; n++) if (e.indexOf(t[n]) > 0) {
            i = !1;
            break
        }
        return i
    }
    e.exports = i()
},
function(e, t, i) {
    function n(e) {
        e !== c && (c = e, s.doc[0].src = e)
    }
    function r() {
        var e = "";
        e = "1" == a.get().isBoard ? s.board_url: s.curr_file && "0" != s.curr_file ? s.doc_url + "/" + s.curr_file + "/" + s.curr_page + ".jpg": "//cnstatic01.e.vhall.com/static/img/mobile/doc_noloading.png",
        n(e)
    }
    var o = i(9),
    a = (i(1), i(2)),
    s = {
        doc_url: "",
        curr_file: "0",
        curr_page: "",
        totalPage: "",
        board_url: "//cnstatic01.e.vhall.com/static/img/mobile/blankspace.jpg"
    },
    c = "";
    e.exports = {
        create: function(e) {
            s = $.extend({},
            s, e),
            $(s.docContent).html('<img style="width:100%" src="//cnstatic01.e.vhall.com/static/img/mobile/doc_noloading.png" onerror="this.src = \'//cnstatic01.e.vhall.com/static/img/mobile/doc_error.png\'"/>'),
            s.doc = $(s.docContent).find("img"),
            r()
        },
        setter: function(e, t) {
            1 == a.getter("type") ? setTimeout(function() {
                o(e) ? s = $.extend({},
                s, e) : s[e] = t,
                r()
            },
            15e3) : (o(e) ? s = $.extend({},
            s, e) : s[e] = t, r())
        },
        getter: function(e) {
            return e ? s[e] : s
        }
    }
},
function(e, t, i) {
    var n = i(5),
    r = i(14),
    o = i(2),
    a = i(1),
    s = {
        init: function(e) {
            if (!n) {
                this.player = new r(e);
                var t = this;
                this.on = function(e, i) {
                    t.player.on(e, i)
                },
                this.setPlayerLine = function(e) {
                    t.player.setPlayerLine(e)
                },
                this.setPlayerDefinition = function(e) {
                    t.player.setPlayerDefinition(e)
                },
                this.setDefaultDefinitions = function(e) {
                    t.player.setDefaultDefinitions = e
                },
                1 == o.getter("type") && (this.seek = function(e) {
                    t.player.seek(e)
                }),
                a.trigger("playerReady"),
                this.player.onready()
            }
        }
    };
    e.exports = s
},
function(e, t, i) {
    var n = i(1),
    r = i(3),
    o = {
        eventProcessors: {},
        init: function(e) {
            console.log(e),
            this.options = e,
            this.pushstream = new PushStream({
                host: e.domain,
                port: e.port,
                modes: "websocket|longpolling",
                messagesPublishedAfter: 0,
                useSSL: "https:" === window.location.protocol,
                messagesControlByArgument: !0
            }),
            this.pushstream.addChannel(e.roomid),
            this.pushstream.connect(),
            this.bind()
        },
        onevent: function(e, t) {
            return "string" != typeof e ? console.log("事件类型必须传入string") : void(this.eventProcessors[e] = t)
        },
        bind: function() {
            var e = this;
            this.pushstream.onmessage = function(t) {
                var i;
                try {
                    i = JSON.parse(t)
                } catch(e) {
                    return void console.log(e, t)
                }
                try {
                    e.eventProcessors[i.event](i)
                } catch(e) {
                    return void console.log(e, i)
                }
            },
            this.onevent("online",
            function(e) {
                n.trigger("userOnline", e)
            }),
            this.onevent("offline",
            function(e) {
                n.trigger("userOffline", e)
            }),
            this.onevent("msg",
            function(e) {
                var t = VHALL_SDK.getUserinfo();
                return ! (e.user_id == t.userid || t.forbidchat && "user" == e.role || e.data.notpublic && "host" != e.role) && (e.avatar = e.avatar ? e.avatar: "//cnstatic01.e.vhall.com/static/images/watch/head50.png", e.content = r(e.data.text), void n.trigger("chatMsg", e))
            }),
            this.onevent("question",
            function(e) {
                var t = VHALL_SDK.getUserinfo();
                return ("question" != e.data.type || "user" != t.role && t.userid != e.data.join_id) && ((!e.data.answer || "0" != e.data.answer.is_open || "user" != t.role || e.data.join_id == t.userid) && (e.data.content = r(e.data.content), e.data.answer && (e.data.answer.content = r(e.data.answer.content)), void n.trigger("questionMsg", e)))
            })
        },
        sendChat: function(e) {
            $.ajax({
                url: o.options.pubUrl,
                type: "get",
                dataType: "jsonp",
                jsonp: "callback",
                data: {
                    token: o.options.token,
                    event: "msg",
                    app_key: o.options.app_key,
                    data: JSON.stringify(e)
                },
                success: function(e) {
                    e = JSON.parse(e);
                    var t = {
                        code: 2e4,
                        msg: "请求成功"
                    };
                    "200" != e.code && (t = {
                        code: 20005,
                        msg: "接口请求失败"
                    }),
                    n.trigger("sendChat", t)
                },
                error: function(e) {
                    n.trigger("sendChat", {
                        code: 20005,
                        msg: "接口请求失败"
                    })
                }
            })
        },
        sendQuestion: function(e) {
            $.ajax({
                url: "//e.vhall.com/api/jssdk/v1/question/addquestion",
                type: "get",
                dataType: "jsonp",
                jsonp: "callback",
                data: {
                    token: o.options.token,
                    content: e,
                    app_key: o.options.app_key
                },
                success: function(e) {
                    var t = {
                        code: 2e4,
                        msg: "请求成功"
                    };
                    "200" != e.code && (t = {
                        code: 20005,
                        msg: "接口请求失败"
                    }),
                    n.trigger("sendQuestion", t)
                },
                error: function(e) {
                    n.trigger("sendQuestion", {
                        code: 20005,
                        msg: "接口请求失败"
                    })
                }
            })
        }
    };
    e.exports = o
},
function(e, t) {
    function i(e) {
        return "[object Object]" === Object.prototype.toString.call(e)
    }
    e.exports = i
},
function(e, t) {
    e.exports = {
        10000 : "消息体格式不正确",
        10001 : "输入不能为空",
        10002 : "当前用户被禁言",
        10003 : "聊天输入不能超过140个字符",
        10004 : "当前已开启全员禁言",
        10005 : "当前活动不在直播",
        10006 : "当前活动未开启问答",
        20000 : "接口请求成功",
        20005 : "没有数据"
    }
},
function(e, t, i) {
    function n(e) {
        this.content = a(JSON.parse(e.content).text),
        this.avatar = e.avatar ? e.avatar: "//cnstatic01.e.vhall.com/static/images/watch/head50.png",
        this.role = e.user_role,
        this.user_name = e.user_name,
        this.time = e.created_at,
        this.user_id = e.user_id
    }
    var r = i(1),
    o = i(10),
    a = i(3),
    s = i(8);
    e.exports = {
        sign: 0,
        getLiveChatMsg: function() {
            return this.sign ? void $.ajax({
                url: "//e.vhall.com/api/jssdk/v1/webinar/historymsg",
                dataType: "jsonp",
                jsonp: "callback",
                data: {
                    webinar_id: VHALL_SDK.options.roomid
                },
                success: function(e) {
                    if ("200" == e.code) {
                        var t = [],
                        i = 0;
                        for (e.data.length; i < e.data.length; i++) t.push(new n(e.data[i]));
                        r.trigger("vhall_live_history_chat_msg", {
                            code: 200,
                            data: t,
                            msg: "拉取数据成功"
                        })
                    } else r.trigger("vhall_live_history_chat_msg", {
                        code: 20005,
                        msg: o[20005]
                    })
                },
                error: function(e) {
                    r.trigger("vhall_live_history_chat_msg", {
                        code: 20005,
                        msg: o[20005]
                    })
                }
            }) : r.trigger("error", {
                code: 1006,
                msg: "sign error"
            })
        },
        getRecordChatMsg: function(e) {
            return this.sign ? void $.ajax({
                url: "//e.vhall.com/api/jssdk/v1/webinar/getmsg",
                dataType: "jsonp",
                jsonp: "callback",
                data: {
                    webinar_id: VHALL_SDK.options.roomid,
                    curr_page: e
                },
                success: function(e) {
                    if ("200" == e.code) {
                        var t = [],
                        i = 0;
                        for (e.data.data.length; i < e.data.data.length; i++) t.push(new n(e.data.data[i]));
                        r.trigger("vhall_record_history_chat_msg", {
                            code: 200,
                            curr_page: e.data.curr_page,
                            total: e.data.total,
                            data: t,
                            total_page: e.data.total_page,
                            msg: "拉取数据成功"
                        })
                    } else r.trigger("vhall_record_history_chat_msg", {
                        code: 20005,
                        msg: o[20005]
                    })
                },
                error: function(e) {
                    r.trigger("vhall_record_history_chat_msg", {
                        code: 20005,
                        msg: o[20005]
                    })
                }
            }) : r.trigger("error", {
                code: 1006,
                msg: "sign error"
            })
        },
        sendRecordChat: function(e) {
            if (!this.sign) return r.trigger("error", {
                code: 1006,
                msg: "sign error"
            });
            var t = this;
            $.ajax({
                url: "//e.vhall.com/api/jssdk/v1/webinar/addmsg",
                dataType: "jsonp",
                jsonp: "callback",
                data: e,
                success: function(e) {
                    "200" == e.code ? (r.trigger("sendChat", {
                        code: 2e4,
                        msg: "请求成功"
                    }), t.getRecordChatMsg(1)) : r.trigger("sendChat", {
                        code: 20005,
                        msg: "接口请求失败"
                    })
                },
                error: function(e) {
                    r.trigger("sendChat", {
                        code: 20005,
                        msg: "接口请求失败"
                    })
                }
            })
        },
        getQuestionlist: function() {
            return this.sign ? 1 != VHALL_SDK.getRoominfo().openQuestion ? r.trigger("error", {
                code: 10006,
                msg: o[10006]
            }) : 1 != VHALL_SDK.getRoominfo().type ? r.trigger("error", {
                code: 10005,
                msg: o[10005]
            }) : void $.ajax({
                url: "//e.vhall.com/api/jssdk/v1/question/list",
                dataType: "jsonp",
                jsonp: "callback",
                data: {
                    token: s.options.token
                },
                success: function(e) {
                    if ("200" == e.code) {
                        var t = [],
                        i = 0;
                        for (e.data.length; i < e.data.length; i++) e.data[i].content = a(e.data[i].content),
                        e.data[i].answer && (e.data[i].answer.content = a(e.data[i].answer.content)),
                        t.push({
                            data: e.data[i]
                        });
                        r.trigger("getQuestionList", {
                            code: 200,
                            data: t,
                            msg: "请求成功"
                        })
                    } else r.trigger("getQuestionList", e)
                },
                error: function(e) {
                    r.trigger("getQuestionList", e)
                }
            }) : r.trigger("error", {
                code: 1006,
                msg: "sign error"
            })
        }
    }
},
function(e, t, i) {
    t = e.exports = i(13)(),
    t.push([e.id, ".facebox-pc{background:#f7f7f7;padding:2px;border:1px solid #afafaf;position:absolute;display:none;z-index:2;top:-266px;left:0}.facebox-pc table td{padding:0}.facebox-pc table td img{cursor:pointer;border:1px solid #f7f7f7}.facebox-pc table td img:hover{border:1px solid #06c}.facebox-mobile{background:#fff;border-top:1px solid #ddd;position:absolute;display:none;z-index:2;overflow:hidden;height:170px;padding:0 1%;top:-170px}.facebox-mobile .qqFace-mobile{float:left}.facebox-mobile .qqFace{float:left;text-align:left}.facebox-mobile li{display:inline-block;padding:10px 0;width:14%;text-align:center}.facebox-mobile .text-center a{width:10px;height:10px;border-radius:100%;background:#ddd;border:none;color:#fff;margin:5px 15px 0 0;display:inline-block;text-decoration:none}.facebox-mobile .text-center a.active{background:#ff3334}.facebox-mobile .text-center{text-align:center}", ""])
},
function(e, t) {
    e.exports = function() {
        var e = [];
        return e.toString = function() {
            for (var e = [], t = 0; t < this.length; t++) {
                var i = this[t];
                i[2] ? e.push("@media " + i[2] + "{" + i[1] + "}") : e.push(i[1])
            }
            return e.join("")
        },
        e.i = function(t, i) {
            "string" == typeof t && (t = [[null, t, ""]]);
            for (var n = {},
            r = 0; r < this.length; r++) {
                var o = this[r][0];
                "number" == typeof o && (n[o] = !0)
            }
            for (r = 0; r < t.length; r++) {
                var a = t[r];
                "number" == typeof a[0] && n[a[0]] || (i && !a[2] ? a[2] = i: i && (a[2] = "(" + a[2] + ") and (" + i + ")"), e.push(a))
            }
        },
        e
    }
},
function(e, t, i) {
    function n(e) {
        return this.currDefinitions = "",
        this.currCdnServerLine = e.lines[0],
        this.stream = e.stream,
        this.clientList = {},
        this.cdnServerLine = {},
        this.definitions = {},
        this.type = r.getter("type"),
        this.getLines(e.lines),
        this.getDefinitions(e.definitions),
        this.$videoCont = $(e.videoCont),
        this.video = this.createVideo(this.$videoCont),
        a.init(),
        this.bind(),
        this
    }
    var r = i(2),
    o = i(6),
    a = i(17),
    s = {
        A: "纯音频",
        SD: "标清",
        HD: "高清"
    };
    n.prototype.getLines = function(e) {
        for (var t = e.length,
        i = 0; i < t; i++) this.cdnServerLine[e[i].name] = e[i]
    },
    n.prototype.onready = function() {
        2 != this.type && (this.trigger("canPlayLines", this.cdnServerLine), this.getCanPlayDefinitions())
    },
    n.prototype.setDefaultDefinitions = function(e) {
        for (var t = 0,
        i = e.length; t < i; t++) this.definitions[s[e[t]]].valid = 1;
        this.getDefinitions(this.definitions),
        this.getCanPlayDefinitions()
    },
    n.prototype.getCanPlayDefinitions = function() {
        var e = {};
        for (var t in this.definitions) this.definitions[t].valid && (e[t] = this.definitions[t]);
        this.trigger("canPlayDefinitions", e)
    },
    n.prototype.getDefinitions = function(e) {
        if (1 == this.type) {
            this.definitions["原画质"] = {
                key: "D",
                name: "原画质",
                valid: 1,
                value: "",
                weight: -1
            };
            for (var t = e.length,
            i = 0; i < t; i++) this.definitions[e[i].name] = e[i]
        } else 3 == this.type && (this.definitions = {
            "原画质": {
                key: "D",
                name: "原画质",
                valid: 1,
                value: "",
                weight: 1
            },
            "纯音频": {
                key: "A",
                name: "纯音频",
                valid: 1,
                value: "",
                weight: 0
            }
        });
        2 != this.type && (this.currDefinitions = this.definitions["原画质"])
    },
    n.prototype.createVideo = function(e) {
        var t = $("<video></video>");
        t.attr("webkit-playsinline", ""),
        t.attr("playsinline", ""),
        t.attr("controls", ""),
        t.css({
            width: "100%",
            height: "100%"
        });
        var i = "";
        return this.currCdnServerLine ? 1 == this.type ? i = this.currCdnServerLine.srv.replace("{stream}", this.stream + this.currDefinitions.value) : 3 == this.type && (i = this.currCdnServerLine.srv) : (console.log("当前无播放线路！"), this.trigger("playerError", {
            msg: "当前无播放线路！"
        })),
        t.attr("src", i),
        e.append(t),
        t[0]
    },
    n.prototype.seek = function(e) {
        this.type && (this.video.currentTime = e, this.trigger("seek", e))
    },
    n.prototype.on = function(e, t) {
        this.clientList[e] || (this.clientList[e] = []),
        this.clientList[e].push(t)
    },
    n.prototype.trigger = function() {
        var e = Array.prototype.slice.call(arguments),
        t = Array.prototype.shift.call(e),
        i = this.clientList[t];
        if (i && i.length >= 0) for (var n = 0,
        r = i.length; n < r; n++) i[n].apply(null, e)
    },
    n.prototype.bind = function() {
        var e = this;
        this.video.addEventListener("waiting",
        function() {
            e.trigger("waiting")
        },
        !1),
        this.video.addEventListener("play",
        function() {
            e.trigger("play")
        },
        !1),
        this.video.addEventListener("pause",
        function() {
            e.trigger("pause")
        },
        !1),
        1 == this.type ? this.video.addEventListener("timeupdate",
        function() {
            e.trigger("timeupdate")
        },
        !1) : this.recordTimeupdate()
    },
    n.prototype.recordTimeupdate = function() {
        var e = 0,
        t = -1,
        i = "",
        n = "no",
        a = null;
        $.ajax({
            url: "//e.vhall.com/api/jssdk/v1/webinar/cuepoint",
            type: "GET",
            dataType: "jsonp",
            jsonp: "callback",
            data: {
                roomid: r.getter("id"),
                app_key: VHALL_SDK.options.appkey
            },
            success: function(e) {
                if ($.trim(e.data)) {
                    var t = $.parseJSON(e.data).cuepoint;
                    if ("object" == typeof t) {
                        for (var s = [], c = !1, d = !1, u = 0, l = t.length - 1; u <= l; u++) {
                            var p = $.parseJSON(t[u].content);
                            "flipOver" != p.type && "change_showtype" != p.type || s.push({
                                created_at: t[u].created_at,
                                type: p.type,
                                page: p.page ? p.page: "",
                                doc: p.doc ? p.doc: "",
                                showType: p.showType
                            }),
                            0 === t[u].created_at && ("flipOver" === p.type ? (p.doc && p.page ? o.create({
                                curr_file: p.doc,
                                curr_page: p.page
                            }) : o.create({
                                curr_file: "",
                                curr_page: ""
                            }), n = i, d = !0) : "change_showtype" === p.type && (r.setter("isBoard", p.showType), o.create({}), 1 === p.showType ? c = !0 : 0 === p.showType && (c = !1)))
                        }
                        a = s
                    }
                }
            }
        }),
        this.video.addEventListener("timeupdate",
        function() {
            var i = this.currentTime;
            if (e = parseInt(i), a && e != t) {
                for (var n = a.length - 1; n >= 0; n--) {
                    var s = a[n];
                    if (e > parseFloat(s.created_at)) {
                        if ("flipOver" === s.type) {
                            o.setter({
                                curr_file: s.doc,
                                curr_page: s.page
                            });
                            break
                        }
                        if ("change_showtype" === s.type) {
                            r.setter("isBoard", s.showType),
                            o.setter({});
                            break
                        }
                    }
                }
                t = e
            }
        },
        !1)
    },
    n.prototype.setPlayerLine = function(e) {
        if (this.cdnServerLine[e]) {
            var t = "",
            i = this.cdnServerLine[e];
            1 == this.type ? t = i.srv.replace("{stream}", r.getter("id") + this.currDefinitions.value) : 3 == this.type && (t = i.srv),
            this.currCdnServerLine = i,
            this.video.src = t,
            this.video.play()
        }
    },
    n.prototype.setPlayerDefinition = function(e) {
        if (1 == this.type) {
            if (this.definitions[e]) {
                var t = this.currCdnServerLine.srv.replace("{stream}", r.getter("id") + this.definitions[e].value);
                this.currDefinitions = this.definitions[e],
                this.video.src = t,
                this.video.play()
            }
        } else 3 == this.type && (this.currCdnServerLine.srv_audio && "纯音频" === e ? (this.video.src = this.currCdnServerLine.srv_audio, this.video.play()) : (this.video.src = this.currCdnServerLine.srv, this.video.play()))
    },
    e.exports = n
},
function(e, t, i) {
    function n(e) {
        if ("undefined" == typeof e) return e = {},
        console.error("请先阅读文档传入正确参数");
        if (! (e.roomid && e.app_key && e.signedat && e.sign && e.username)) return console.error("有必填项未填");
        var t = {
            account: e.account,
            email: e.email,
            roomid: e.roomid,
            username: e.username,
            appkey: e.app_key,
            signedat: e.signedat,
            sign: e.sign,
            facedom: e.facedom,
            textdom: e.textdom,
            videoContent: e.videoContent,
            docContent: e.docContent
        };
        VHALL_SDK.options = t,
        o(["//cnstatic01.e.vhall.com/3rdlibs/nginx-push-stream/0.5.1/pushstream.js", "//cnstatic01.e.vhall.com/3rdlibs/socket.io/1.3.5/socket.io.min.js", "//cnstatic01.e.vhall.com/3rdlibs/jquery-json/2.4.0/jquery.json.min.js", "//cnstatic01.e.vhall.com/3rdlibs/base64/base64.js"],
        function() {
            r(t)
        })
    }
    var r = i(16),
    o = i(22);
    e.exports = n
},
function(e, t, i) {
    function n(e) {
        var t = {
            roomid: e.roomid,
            account: e.account,
            username: e.username,
            app_key: e.appkey,
            signedat: e.signedat,
            sign: e.sign
        };
        e.email && (t.email = e.email),
        $.ajax({
            url: "//e.vhall.com/api/jssdk/v1/webinar/init",
            type: "get",
            dataType: "jsonp",
            jsonp: "callback",
            data: t,
            success: function(t) {
                200 == t.code ? (t.data.roomid = e.roomid, o(t.data, e), c.trigger("ready")) : c.trigger("error", t)
            },
            error: function(e) {
                c.trigger("error", e)
            }
        })
    }
    function r(e, t) {
        var i = e.visitor;
        d.create({
            id: e.roomid,
            type: e.webinarStatus,
            openQuestion: e.openQuestion,
            isBoard: e.isBoard
        }),
        u.create({
            username: i.nick_name,
            userid: i.id,
            sessionId: e.sessionId,
            forbidchat: e.forbidchat ? e.forbidchat: 0,
            role: i.role ? i.role: "user",
            avatar: i.avatar ? i.avatar: "//cnstatic01.e.vhall.com/static/images/watch/head50.png",
            is_gag: e.visitor.is_gag,
            is_kickout: e.visitor.is_kickout
        }),
        t.docContent && !f && l.create({
            doc_url: e.doc.srv,
            curr_file: e.doc.currFile,
            curr_page: e.doc.currPage,
            totalPage: e.doc.totalPage,
            docContent: t.docContent
        })
    }
    function o(e, t) {
        r(e, t),
        1 == e.webinarStatus && (s.init({
            pubUrl: e.pushstreamPubUrl,
            token: e.socketToken,
            domain: e.pushstreamDomain,
            port: e.pushstreamPort,
            roomid: t.roomid
        }), a.init({
            srv: e.socketSrv,
            token: e.socketToken,
            app: "vhall"
        })),
        p.sign = 1,
        t.facedom && t.textdom && $(t.facedom).qqFace({
            assign: t.textdom,
            path: "//cnstatic01.e.vhall.com/static/img/arclist/"
        }),
        t.videoContent && g.init($.extend({
            videoCont: t.videoContent
        },
        e.mobilePlayer))
    }
    var a = i(18),
    s = i(8),
    c = i(1),
    d = i(2),
    u = i(4),
    l = i(6);
    i(3);
    var p = i(11),
    g = i(7),
    f = i(5);
    e.exports = n
},
function(e, t, i) {
    function n() {
        var e = r();
        return e = e.match(/(https?:|rtmp:)?(\/\/)?([a-zA-Z0-9]+\.)?([a-zA-Z0-9]+\.)+[a-zA-Z0-9]+/),
        e && (e = e[0]),
        e
    }
    function r() {
        var e = "";
        return $("video")[0] && $("video")[0].paused ? e = $("video")[0].src: $("audio")[0] ? e = $("audio")[0].src: $("video")[0] && (e = $("video")[0].src),
        e
    }
    function o() {
        return (new Date).getTime() - b
    }
    function a() {
        return {
            p: D.p,
            pf: "3",
            aid: D.p,
            uid: C.join_uid,
            s: D.s
        }
    }
    function s() {
        var e = a();
        e.tt = o(),
        1 == C.webinar_type ? e.sd = n() : e.fd = r(),
        e.ua = D.ua,
        j({
            k: v.heart_beat,
            id: String(D.s) + (new Date).getTime(),
            s: D.s,
            token: e
        })
    }
    function c() {
        var e = a();
        1 == C.webinar_type ? e.sd = n() : e.fd = r(),
        j({
            k: v.player_pause,
            id: String(D.s) + (new Date).getTime(),
            s: D.s,
            token: e
        })
    }
    function d() {
        var e = a();
        1 == C.webinar_type ? e.sd = n() : e.fd = r(),
        j({
            k: v.player_resume,
            id: String(D.s) + (new Date).getTime(),
            s: D.s,
            token: e
        })
    }
    function u() {
        k = 0,
        w = 0;
        var e = a();
        1 == C.webinar_type ? e.sd = n() : e.fd = r(),
        j({
            k: v.open_stream,
            id: String(D.s) + (new Date).getTime(),
            s: D.s,
            token: e
        })
    }
    function l() {
        var e = a();
        1 == C.webinar_type ? e.sd = n() : e.fd = r(),
        e.tt = o(),
        e._bc = k,
        e._bt = "",
        j({
            k: v.close_stream,
            id: String(D.s) + (new Date).getTime(),
            s: D.s,
            token: e
        })
    }
    function p() {
        var e = a();
        1 == C.webinar_type ? e.sd = n() : e.fd = r(),
        e.tt = (new Date).getTime() - b,
        e._bc = L,
        e._bt = S,
        j({
            k: v.lag,
            id: String(D.s) + (new Date).getTime(),
            s: D.s,
            token: e
        })
    }
    function g() {
        var e = f.get(),
        t = h.get();
        C.webinar_id = e.id,
        C.webinar_type = e.type,
        C.sessionId = t.sessionId,
        C.join_uid = t.userid,
        D = {
            p: C.webinar_id,
            s: C.sessionId,
            ua: navigator.userAgent
        },
        setTimeout(function() {
            var e = 1;
            $("video")[0] ? e = $("video")[0].paused: $("audio")[0] && (e = $("audio")[0].paused),
            x || e || (u(this.src), x = setInterval(function() {
                s(),
                p()
            },
            6e4))
        },
        1e3),
        $("video")[0] && ($("video")[0].addEventListener("play",
        function() {
            b = (new Date).getTime(),
            x = setInterval(function() {
                s(),
                p()
            },
            6e4),
            _ && (u(this.src), _ = !1),
            w = 0,
            d()
        },
        !1), $("video")[0].addEventListener("timeupdate",
        function() {
            w && (S = (new Date).getTime() - w)
        },
        !1), $("video")[0].addEventListener("pause",
        function() {
            w = 0,
            clearInterval(x),
            c()
        },
        !1), $("video")[0].addEventListener("ended",
        function() {
            w = 0,
            clearInterval(x)
        },
        !1), $("video")[0].addEventListener("waiting",
        function() {
            L++,
            k++,
            w = (new Date).getTime()
        })),
        $("audio")[0] && ($("audio")[0].addEventListener("play",
        function() {
            b = (new Date).getTime(),
            x = setInterval(function() {
                s(),
                p()
            },
            6e4),
            _ && (u(this.src), _ = !1),
            w = 0,
            d()
        },
        !1), $("audio")[0].addEventListener("timeupdate",
        function() {
            w && (S = (new Date).getTime() - w)
        },
        !1), $("audio")[0].addEventListener("pause",
        function() {
            w = 0,
            clearInterval(x),
            c()
        },
        !1), $("audio")[0].addEventListener("waiting",
        function() {
            L++,
            k++,
            w = (new Date).getTime()
        }), $("audio")[0].addEventListener("ended",
        function() {
            w = 0,
            clearInterval(x)
        },
        !1))
    }
    var f = i(2),
    h = i(4),
    v = {
        open_stream: 92001,
        close_stream: 92002,
        heart_beat: 92003,
        player_pause: 92004,
        player_resume: 92005,
        lag: 94001
    },
    m = "//la.e.vhall.com:1780/login",
    y = {},
    b = (new Date).getTime(),
    _ = !0,
    x = null,
    k = 0,
    L = 0,
    w = 0,
    S = 0,
    C = {},
    D = {
        p: C.webinar_id,
        s: C.sessionId,
        ua: navigator.userAgent
    },
    j = function(e) {
        e.token = Base64.encode(JSON.stringify(e.token)),
        $.getJSON(m, e,
        function() {})
    },
    A = {},
    T = null;
    T = function(e, t) {
        A[e] || (A[e] = []),
        A[e].push(t)
    },
    y.trigger = function() {
        var e = Array.prototype.slice.call(arguments),
        t = Array.prototype.shift.call(e),
        i = A[t];
        if (i && i.length >= 0) for (var n = 0,
        r = i.length; n < r; n++) i[n].apply(null, e)
    },
    T("changeline",
    function(e, t) {
        l(e),
        u(t)
    }),
    y.init = g,
    e.exports = y
},
function(e, t, i) {
    function n(e) {
        switch (e.type) {
        case "*disablechat":
            d.getter("userid") == e.user_id && d.setter("is_gag", 1),
            r.trigger("disableChat", e.user_id);
            break;
        case "*permitchat":
            d.getter("userid") == e.user_id && d.setter("is_gag", 0),
            r.trigger("permitChat", e.user_id);
            break;
        case "*kickout":
            d.getter("userid") == e.user_id && d.setter("is_kickout", 1),
            r.trigger("kickout", e.user_id);
            break;
        case "*kickoutrestore":
            d.getter("userid") == e.user_id && d.setter("is_kickout", 0),
            r.trigger("kickoutRestore", e.user_id);
            break;
        case "*forbidchat":
            "1" === e.status ? d.setter("forbidchat", 1) : d.setter("forbidchat", 0),
            r.trigger("forbidChat", e.status);
            break;
        case "*question":
            "1" == e.status ? c.setter("openQuestion", 1) : c.setter("openQuestion", 0),
            r.trigger("questionSwitch", {
                status: e.status
            });
            break;
        case "*publish_start":
            u.player.setPlayerDefinition(e.trans);
            break;
        case "*whiteBoard":
            a ? c.setter("isBoard", e.status) : (setTimeout(function() {
                c.setter("isBoard", e.status)
            },
            15e3), s.setter({}));
            break;
        case "*over":
            a || setTimeout(function() {
                r.trigger("streamOver")
            },
            15e3);
            break;
        case "*publishStart":
            a || setTimeout(function() {
                r.trigger("publishStart")
            },
            15e3);
            break;
        case "*announcement":
            r.trigger("announcement", e.content)
        }
    }
    var r = i(1),
    o = (i(3), i(19)),
    a = i(5),
    s = i(6),
    c = i(2),
    d = i(4),
    u = i(7);
    e.exports = {
        init: function(e) {
            this.options = e,
            this.socket = io.connect(e.srv, {
                query: "token=" + e.token + "&app=" + (e.app || "vhall"),
                transports: o ? ["polling"] : ["websocket", "polling"]
            }),
            this.bind()
        },
        bind: function() {
            this.socket.on("online",
            function(e) {}),
            this.socket.on("cmd",
            function(e) {
                try {
                    e = $.parseJSON(e)
                } catch(t) {
                    e = e
                }
                n(e)
            }),
            this.socket.on("flashMsg",
            function(e) {
                if (a);
                else try {
                    var t = $.parseJSON(e);
                    "flipOver" == t.type && s.setter({
                        curr_file: t.doc,
                        curr_page: t.page,
                        totalPage: t.totalPage
                    })
                } catch(e) {}
            })
        }
    }
},
function(e, t) {
    function i() {
        var e = !1;
        return "Microsoft Internet Explorer" == navigator.appName && ("MSIE9.0" != navigator.appVersion.split(";")[1].replace(/[ ]/g, "") && "MSIE8.0" != navigator.appVersion.split(";")[1].replace(/[ ]/g, "") || (e = !0)),
        e
    }
    e.exports = i()
},
function(e, t) {
    function i(e) {
        return "[object Number]" === Object.prototype.toString.call(e)
    }
    e.exports = i
},
function(e, t) {
    function i(e) {
        return "[object String]" === Object.prototype.toString.call(e)
    }
    e.exports = i
},
function(e, t) {
    function i(e, t) {
        if ("string" == typeof e) {
            var i = e;
            e = [],
            e.push(i)
        }
        var r = function(e, t) {
            n(e.shift(),
            function() {
                e.length ? r(e, t) : t && t()
            })
        };
        r(e, t)
    }
    function n(e, t) {
        var i = !1,
        n = document.createElement("script");
        n.src = e,
        n.onload = n.onreadystatechange = function() {
            i || this.readyState && "loaded" != this.readyState && "complete" != this.readyState || (i = !0, t && t())
        },
        document.body.appendChild(n)
    }
    e.exports = i
},
function(e, t, i) {
    function n(e) {
        return c.indexOf(e)
    }
    function r(e) {
        return c[e]
    }
    function o(e) {
        return s(e) ? n(e) + 1 : a(e) ? r(e) : null
    }
    var a = i(20),
    s = i(21),
    c = ["[微笑]", "[撇嘴]", "[色]", "[发呆]", "[得意]", "[流泪]", "[害羞]", "[闭嘴]", "[睡]", "[哭]", "[尴尬]", "[发怒]", "[调皮]", "[呲牙]", "[惊讶]", "[难过]", "[酷]", "[汗]", "[抓狂]", "[吐]", "[偷笑]", "[愉快]", "[白眼]", "[傲慢]", "[饥饿]", "[困]", "[惊恐]", "[流汗]", "[憨笑]", "[悠闲]", "[奋斗]", "[咒骂]", "[疑问]", "[嘘]", "[晕]", "[疯了]", "[衰]", "[骷髅]", "[敲打]", "[再见]", "[擦汗]", "[抠鼻]", "[鼓掌]", "[糗大了]", "[坏笑]", "[左哼哼]", "[右哼哼]", "[哈欠]", "[鄙视]", "[委屈]", "[快哭了]", "[阴险]", "[亲亲]", "[吓]", "[可怜]", "[菜刀]", "[西瓜]", "[啤酒]", "[篮球]", "[乒乓]", "[咖啡]", "[饭]", "[猪头]", "[玫瑰]", "[凋谢]", "[嘴唇]", "[爱心]", "[心碎]", "[蛋糕]", "[闪电]", "[炸弹]", "[刀]", "[足球]", "[瓢虫]", "[便便]", "[月亮]", "[太阳]", "[礼物]", "[拥抱]", "[强]", "[弱]", "[握手]", "[胜利]", "[抱拳]", "[勾引]", "[拳头]", "[差劲]", "[爱你]", "[NO]", "[OK]"];
    Array.prototype.indexOf || (Array.prototype.indexOf = function(e) {
        var t = this.length >>> 0,
        i = Number(arguments[1]) || 0;
        for (i = i < 0 ? Math.ceil(i) : Math.floor(i), i < 0 && (i += t); i < t; i++) if (i in this && this[i] === e) return i;
        return - 1
    }),
    e.exports = o
},
function(e, t, i) {
    function n(e, t) {
        for (var i = 0; i < e.length; i++) {
            var n = e[i],
            r = g[n.id];
            if (r) {
                r.refs++;
                for (var o = 0; o < r.parts.length; o++) r.parts[o](n.parts[o]);
                for (; o < n.parts.length; o++) r.parts.push(d(n.parts[o], t))
            } else {
                for (var a = [], o = 0; o < n.parts.length; o++) a.push(d(n.parts[o], t));
                g[n.id] = {
                    id: n.id,
                    refs: 1,
                    parts: a
                }
            }
        }
    }
    function r(e) {
        for (var t = [], i = {},
        n = 0; n < e.length; n++) {
            var r = e[n],
            o = r[0],
            a = r[1],
            s = r[2],
            c = r[3],
            d = {
                css: a,
                media: s,
                sourceMap: c
            };
            i[o] ? i[o].parts.push(d) : t.push(i[o] = {
                id: o,
                parts: [d]
            })
        }
        return t
    }
    function o(e, t) {
        var i = v(),
        n = b[b.length - 1];
        if ("top" === e.insertAt) n ? n.nextSibling ? i.insertBefore(t, n.nextSibling) : i.appendChild(t) : i.insertBefore(t, i.firstChild),
        b.push(t);
        else {
            if ("bottom" !== e.insertAt) throw new Error("Invalid value for parameter 'insertAt'. Must be 'top' or 'bottom'.");
            i.appendChild(t)
        }
    }
    function a(e) {
        e.parentNode.removeChild(e);
        var t = b.indexOf(e);
        t >= 0 && b.splice(t, 1)
    }
    function s(e) {
        var t = document.createElement("style");
        return t.type = "text/css",
        o(e, t),
        t
    }
    function c(e) {
        var t = document.createElement("link");
        return t.rel = "stylesheet",
        o(e, t),
        t
    }
    function d(e, t) {
        var i, n, r;
        if (t.singleton) {
            var o = y++;
            i = m || (m = s(t)),
            n = u.bind(null, i, o, !1),
            r = u.bind(null, i, o, !0)
        } else e.sourceMap && "function" == typeof URL && "function" == typeof URL.createObjectURL && "function" == typeof URL.revokeObjectURL && "function" == typeof Blob && "function" == typeof btoa ? (i = c(t), n = p.bind(null, i), r = function() {
            a(i),
            i.href && URL.revokeObjectURL(i.href)
        }) : (i = s(t), n = l.bind(null, i), r = function() {
            a(i)
        });
        return n(e),
        function(t) {
            if (t) {
                if (t.css === e.css && t.media === e.media && t.sourceMap === e.sourceMap) return;
                n(e = t)
            } else r()
        }
    }
    function u(e, t, i, n) {
        var r = i ? "": n.css;
        if (e.styleSheet) e.styleSheet.cssText = _(t, r);
        else {
            var o = document.createTextNode(r),
            a = e.childNodes;
            a[t] && e.removeChild(a[t]),
            a.length ? e.insertBefore(o, a[t]) : e.appendChild(o)
        }
    }
    function l(e, t) {
        var i = t.css,
        n = t.media;
        if (n && e.setAttribute("media", n), e.styleSheet) e.styleSheet.cssText = i;
        else {
            for (; e.firstChild;) e.removeChild(e.firstChild);
            e.appendChild(document.createTextNode(i))
        }
    }
    function p(e, t) {
        var i = t.css,
        n = t.sourceMap;
        n && (i += "\n/*# sourceMappingURL=data:application/json;base64," + btoa(unescape(encodeURIComponent(JSON.stringify(n)))) + " */");
        var r = new Blob([i], {
            type: "text/css"
        }),
        o = e.href;
        e.href = URL.createObjectURL(r),
        o && URL.revokeObjectURL(o)
    }
    var g = {},
    f = function(e) {
        var t;
        return function() {
            return "undefined" == typeof t && (t = e.apply(this, arguments)),
            t
        }
    },
    h = f(function() {
        return /msie [6-9]\b/.test(window.navigator.userAgent.toLowerCase())
    }),
    v = f(function() {
        return document.head || document.getElementsByTagName("head")[0]
    }),
    m = null,
    y = 0,
    b = [];
    e.exports = function(e, t) {
        t = t || {},
        "undefined" == typeof t.singleton && (t.singleton = h()),
        "undefined" == typeof t.insertAt && (t.insertAt = "bottom");
        var i = r(e);
        return n(i, t),
        function(e) {
            for (var o = [], a = 0; a < i.length; a++) {
                var s = i[a],
                c = g[s.id];
                c.refs--,
                o.push(c)
            }
            if (e) {
                var d = r(e);
                n(d, t)
            }
            for (var a = 0; a < o.length; a++) {
                var c = o[a];
                if (0 === c.refs) {
                    for (var u = 0; u < c.parts.length; u++) c.parts[u]();
                    delete g[c.id]
                }
            }
        }
    };
    var _ = function() {
        var e = [];
        return function(t, i) {
            return e[t] = i,
            e.filter(Boolean).join("\n")
        }
    } ()
},
function(e, t, i) {
    var n = i(12);
    "string" == typeof n && (n = [[e.id, n, ""]]);
    i(24)(n, {});
    n.locals && (e.exports = n.locals)
}]);