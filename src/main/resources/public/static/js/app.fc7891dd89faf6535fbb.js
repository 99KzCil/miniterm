webpackJsonp([0],{0:function(e,t){},"4xBg":function(e,t){},"7zck":function(e,t){},"8IKe":function(e,t){},NHnr:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s,o=n("7+uW"),i=n("3EgV"),a=n.n(i),r=n("8+8L"),l={user:null},c=n("/ocq"),d={data:function(){return{user:{},valid:!1,working:!1,loaded:!1}},mounted:function(){var e=this;s=this,bus.$off("logout"),bus.$on("logout",function(){e.$http.post("/api/login/logout").then(function(){M.replace("/")})}),this.$http.post("/api/login/check").then(u,u)},methods:{login:function(){this.working=!0,this.$http.post("/api/login",this.user).then(u,u)}}};function u(e){200==e.status?(l.user=e.data,M.replace("/home/"),s.working=!1,s.loaded=!0):(l.user=null,s.working=!1,s.loaded=!0)}var v={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return e.loaded?n("v-app",[n("v-container",{attrs:{fluid:"","fill-height":""}},[n("v-layout",{attrs:{"align-center":"","justify-center":""}},[n("v-flex",{attrs:{md6:"",xl3:""}},[n("v-card",[n("v-toolbar",{attrs:{flat:"",color:"blue-grey",dark:""}},[n("v-toolbar-title",[e._v("miniterm login")])],1),e._v(" "),n("v-card-title",{attrs:{"primary-title":""}},[n("v-flex",[n("v-form",{on:{submit:function(t){return t.stopPropagation(),t.preventDefault(),e.login(t)}}},[n("v-text-field",{staticStyle:{display:"none"},attrs:{label:"hidden"}}),e._v(" "),n("v-text-field",{attrs:{label:"username",required:""},model:{value:e.user.username,callback:function(t){e.$set(e.user,"username",t)},expression:"user.username"}}),e._v(" "),n("v-text-field",{attrs:{type:"password",label:"password",required:""},model:{value:e.user.password,callback:function(t){e.$set(e.user,"password",t)},expression:"user.password"}}),e._v(" "),n("v-btn",{staticClass:"ma-0 text-lowercase",attrs:{type:"submit",loading:e.working,dark:"",color:"brown"},on:{click:function(t){return t.stopPropagation(),t.preventDefault(),e.login(t)}}},[e._v("Login")])],1)],1)],1)],1)],1)],1)],1)],1):e._e()},staticRenderFns:[]};var f=n("VU/8")(d,v,!1,function(e){n("aNIP")},null,null).exports,p={data:function(){return{title:"",dialog:!1,valid:!1,connection:{},rules:[function(e){return!!e||"Required"}]}},mounted:function(){var e=this;bus.$off("showConnectionDialog"),bus.$on("showConnectionDialog",function(t){e.dialog=!0,e.connection=t,e.title=t.id?"edit connection":"add connection"})},methods:{saveConnection:function(){var e=this;this.$refs.form.validate(),this.valid&&this.$http.post("/api/connection/save",this.connection).then(function(){e.dialog=!1,bus.$emit("loadConnections")})}}},m={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-dialog",{attrs:{width:"400"},model:{value:e.dialog,callback:function(t){e.dialog=t},expression:"dialog"}},[n("v-card",[n("v-card-title",{staticClass:"headline blue-grey white--text",attrs:{dark:""}},[n("v-icon",{attrs:{left:"",dark:""}},[e._v("desktop_windows")]),e._v(" "),n("span",{staticClass:"ml-4"},[e._v(e._s(e.title))]),e._v(" "),n("v-spacer"),e._v(" "),n("v-icon",{attrs:{dark:""},on:{click:function(t){e.dialog=!1}}},[e._v("cancel")])],1),e._v(" "),n("v-card-text",{staticClass:"pa-4"},[n("v-form",{ref:"form",model:{value:e.valid,callback:function(t){e.valid=t},expression:"valid"}},[n("v-text-field",{staticStyle:{display:"none"},attrs:{label:"hidden"}}),e._v(" "),n("v-text-field",{attrs:{rules:e.rules,label:"name",required:""},model:{value:e.connection.name,callback:function(t){e.$set(e.connection,"name",t)},expression:"connection.name"}}),e._v(" "),n("v-text-field",{attrs:{rules:e.rules,label:"host",required:""},model:{value:e.connection.host,callback:function(t){e.$set(e.connection,"host",t)},expression:"connection.host"}}),e._v(" "),n("v-text-field",{attrs:{rules:e.rules,label:"port",required:"",type:"number"},model:{value:e.connection.port,callback:function(t){e.$set(e.connection,"port",t)},expression:"connection.port"}}),e._v(" "),n("v-text-field",{attrs:{rules:e.rules,label:"username",required:""},model:{value:e.connection.username,callback:function(t){e.$set(e.connection,"username",t)},expression:"connection.username"}}),e._v(" "),n("v-text-field",{attrs:{rules:e.rules,label:"password",required:"",type:"password"},model:{value:e.connection.password,callback:function(t){e.$set(e.connection,"password",t)},expression:"connection.password"}}),e._v(" "),n("v-text-field",{attrs:{disabled:!0,type:"password",label:"privateKey"},model:{value:e.connection.privateKey,callback:function(t){e.$set(e.connection,"privateKey",t)},expression:"connection.privateKey"}}),e._v(" "),n("v-text-field",{attrs:{disabled:!0,type:"password",label:"privateKeyPassword"},model:{value:e.connection.privateKeyPassword,callback:function(t){e.$set(e.connection,"privateKeyPassword",t)},expression:"connection.privateKeyPassword"}})],1)],1),e._v(" "),n("v-divider"),e._v(" "),n("v-card-actions",{staticClass:"pa-3"},[n("v-btn",{staticClass:"text-lowercase",attrs:{color:"red",dark:""},on:{click:function(t){e.dialog=!1}}},[e._v("cancel")]),e._v(" "),n("v-spacer"),e._v(" "),n("v-btn",{staticClass:"text-lowercase",attrs:{disabled:!e.valid,color:"blue-grey",dark:e.valid},on:{click:e.saveConnection}},[e._v("add")])],1)],1)],1)},staticRenderFns:[]},h={data:function(){return{dialog:!1,working:!1,connection:{}}},mounted:function(){var e=this;bus.$off("showDeleteDialog"),bus.$on("showDeleteDialog",function(t){e.connection=t,e.dialog=!0})},methods:{deleteConnection:function(){var e=this;this.working=!0,this.$http.post("/api/connection/remove",this.connection).then(function(){e.working=!1,e.dialog=!1,bus.$emit("loadConnections")})}}},g={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-dialog",{attrs:{width:"400"},model:{value:e.dialog,callback:function(t){e.dialog=t},expression:"dialog"}},[n("v-card",[n("v-card-title",{staticClass:"headline blue-grey white--text",attrs:{dark:""}},[n("v-icon",{attrs:{left:"",dark:""}},[e._v("desktop_windows")]),e._v(" "),n("span",{staticClass:"ml-4"},[e._v("delete connection")]),e._v(" "),n("v-spacer"),e._v(" "),n("v-icon",{attrs:{dark:""},on:{click:function(t){e.dialog=!1}}},[e._v("cancel")])],1),e._v(" "),n("v-card-text",[e._v(e._s(e.connection.name)+" will be deleted, are you sure?")]),e._v(" "),n("v-card-actions",{staticClass:"pa-3"},[n("v-btn",{staticClass:"text-lowercase",attrs:{color:"red",dark:""},on:{click:function(t){e.dialog=!1}}},[e._v("no")]),e._v(" "),n("v-spacer"),e._v(" "),n("v-btn",{staticClass:"text-lowercase",attrs:{loading:e.working,color:"blue-grey",dark:""},on:{click:e.deleteConnection}},[e._v("yes")])],1)],1)],1)},staticRenderFns:[]},b={components:{ConnectionDialog:n("VU/8")(p,m,!1,null,null,null).exports,ConnectionDeleteDialog:n("VU/8")(h,g,!1,null,null,null).exports},computed:{parent:function(){return this.$parent.$parent.$parent}},methods:{addConnection:function(){bus.$emit("showConnectionDialog",{})},editConnection:function(e){bus.$emit("showConnectionDialog",e)},deleteConnection:function(e){bus.$emit("showDeleteDialog",e)},newSession:function(e){bus.$emit("newSession",e)}},mounted:function(){}},_={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-card",{attrs:{"fill-height":"",height:"100%","elevation-0":""}},[n("v-list",{attrs:{subheader:""}},[n("v-subheader",{staticClass:"headline blue-grey white--text",attrs:{dark:""}},[n("v-img",{staticStyle:{margin:"0 4px"},attrs:{"max-height":"32","max-width":"32",src:"/static/icon.png"}}),e._v(" "),n("span",{staticClass:"ml-3"},[e._v("miniterm")]),e._v(" "),n("v-spacer"),e._v(" "),n("v-btn",{staticClass:"elevation-0 ma-0",attrs:{flat:"",icon:"",small:""},on:{click:function(t){e.$parent.$parent.$parent.hideDrawer=!0}}},[n("v-icon",[e._v("arrow_left")])],1)],1),e._v(" "),n("v-list-tile",{attrs:{avatar:""},on:{click:e.addConnection}},[n("v-list-tile-avatar",[n("v-icon",{attrs:{left:""}},[e._v("desktop_windows")])],1),e._v(" "),n("v-list-tile-content",[n("v-list-tile-title",[e._v("add connection")])],1)],1),e._v(" "),n("v-divider",{attrs:{color:"white"}}),e._v(" "),e._l(e.parent.connections,function(t){return n("v-list-tile",{key:t.id,attrs:{avatar:""},on:{click:function(n){e.newSession(t)}}},[n("v-list-tile-content",[n("v-list-tile-title",[n("v-layout",[e._v("\n            "+e._s(t.name)+"\n            "),n("v-spacer"),e._v(" "),n("v-icon",{attrs:{small:""},on:{click:function(n){n.stopPropagation(),e.editConnection(t)}}},[e._v("edit")])],1)],1),e._v(" "),n("v-list-tile-sub-title",[n("v-layout",[e._v("\n            "+e._s(t.host)+"\n            "),n("v-spacer"),e._v(" "),n("v-icon",{attrs:{color:"red",small:""},on:{click:function(n){n.stopPropagation(),e.deleteConnection(t)}}},[e._v("delete")])],1)],1)],1)],1)}),e._v(" "),0==e.parent.connections.length?n("v-list-tile",{attrs:{avatar:""}},[n("v-list-tile-content",[n("v-list-tile-title",[e._v("No connections added yet.")])],1)],1):e._e()],2),e._v(" "),n("connectionDialog"),e._v(" "),n("connectionDeleteDialog")],1)},staticRenderFns:[]};var w,$,k=n("VU/8")(b,_,!1,function(e){n("oBZ/")},null,null).exports,S={mounted:function(){bus.$off("socketConnected"),bus.$on("socketConnected",this.loadSessions),bus.$off("newSession"),bus.$on("newSession",this.newSession),bus.$off("activateSession"),bus.$on("activateSession",this.activateSession),bus.$off("removeSession"),bus.$on("removeSession",this.removeSession),bus.$off("stateData"),bus.$on("stateData",this.stateData),this.sessions=this.$parent.$parent.sessions,this.loadingSessions=this.$parent.$parent.loadingSessions},methods:{loadSessions:function(){var e=this;this.$http.get("/api/session/getSessions").then(function(t){for(var n in e.sessions.length=0,t.body)t.body.hasOwnProperty(n)&&e.sessions.push(t.body[n]);if(e.sessions.length>0)if(localStorage.currentSessionId){var s=!1;e.sessions.forEach(function(t){if(t.id==localStorage.currentSessionId)return e.activateSession(t),void(s=!0)}),s||e.activateSession(e.sessions[0])}else e.activateSession(e.sessions[0]);e.$parent.$parent.loadingSessions=!1})},newSession:function(e){var t=this;this.$http.post("/api/session/newSession",e.id).then(function(e){t.sessions.push(e.body),t.activateSession(e.body)})},activateSession:function(e){w!=e&&(this.deactivateAll(),e.active=!0,w=e,bus.$emit("activateTerminal",e))},removeSession:function(e){var t=this;this.$http.post("/api/session/remove",e.id).then(function(){t.$parent.$parent.sessions=t.$parent.$parent.sessions.filter(function(t){return t.id!=e.id}),t.sessions=t.$parent.$parent.sessions,w==e&&t.sessions.length>0&&t.activateSession(t.sessions[0]),0==t.sessions.length&&bus.$emit("removeTerminal")})},deactivateAll:function(){this.sessions.forEach(function(e){e.active=!1}),this.$forceUpdate()},stateData:function(e){this.sessions.forEach(function(t){t.id!=e.sessionId||(t.state=e.state)})}}},y={render:function(){var e=this.$createElement;return(this._self._c||e)("div")},staticRenderFns:[]},x=n("VU/8")(S,y,!1,null,null,null).exports,C=n("mvHQ"),D=n.n(C),T=n("O3w4"),E=n.n(T),P={mounted:function(){($=new E.a("/api/ssh")).onopen=this.socketOpened,$.onmessage=this.socketMessageReceived,bus.$off("socketSend"),bus.$on("socketSend",this.socketSend)},beforeDestroy:function(){$.close()},methods:{socketOpened:function(){bus.$emit("socketConnected")},socketSend:function(e){$.send(D()(e))},socketMessageReceived:function(e){var t=JSON.parse(e.data);switch(t.command){case"terminal":bus.$emit("terminalData",t);break;case"setState":bus.$emit("stateData",t)}}}},I={render:function(){var e=this.$createElement;return(this._self._c||e)("div")},staticRenderFns:[]},R=n("VU/8")(P,I,!1,null,null,null).exports,K={data:function(){return{dialog:!1,session:{}}},mounted:function(){var e=this;bus.$off("showCloseSessionDialog"),bus.$on("showCloseSessionDialog",function(t){e.session=t,e.dialog=!0})},methods:{closeSession:function(){this.dialog=!1,bus.$emit("removeSession",this.session)}}},F={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-dialog",{attrs:{width:"400"},model:{value:e.dialog,callback:function(t){e.dialog=t},expression:"dialog"}},[n("v-card",[n("v-card-title",{staticClass:"headline blue-grey white--text",attrs:{dark:""}},[n("v-icon",{attrs:{left:"",dark:""}},[e._v("desktop_windows")]),e._v(" "),n("span",{staticClass:"ml-4"},[e._v("close session")]),e._v(" "),n("v-spacer"),e._v(" "),n("v-icon",{attrs:{dark:""},on:{click:function(t){e.dialog=!1}}},[e._v("cancel")])],1),e._v(" "),n("v-card-text",[e._v("running session will be closed, are you sure?")]),e._v(" "),n("v-card-actions",{staticClass:"pa-3"},[n("v-btn",{staticClass:"text-lowercase",attrs:{color:"red",dark:""},on:{click:function(t){e.dialog=!1}}},[e._v("no")]),e._v(" "),n("v-spacer"),e._v(" "),n("v-btn",{staticClass:"text-lowercase",attrs:{color:"blue-grey",dark:""},on:{click:e.closeSession}},[e._v("yes")])],1)],1)],1)},staticRenderFns:[]},U={components:{CloseSessionDialog:n("VU/8")(K,F,!1,null,null,null).exports},computed:{orderedSessions:function(){return this.sessions.sort(function(e,t){return parseInt(e.order)-parseInt(t.order)})},sessions:function(){return this.$parent.$parent.$parent.sessions}},methods:{getColor:function(e){var t="";return t=e.active?"":"lighten-3 ","stopped"==e.state&&(t+="red"),"closed"==e.state&&(t+="red"),"started"==e.state&&(t+="green"),t},activate:function(e){bus.$emit("activateSession",e)},close:function(e){"started"==e.state?bus.$emit("showCloseSessionDialog",e):bus.$emit("removeSession",e)}}},V={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-layout",{staticClass:"layout",attrs:{"fill-height":"","align-center":""}},[e._l(e.orderedSessions,function(t){return n("v-btn",{key:t.id,attrs:{small:"",color:e.getColor(t)},on:{click:function(n){e.activate(t)},mouseup:function(n){if("button"in n&&1!==n.button)return null;n.stopPropagation(),e.close(t)}}},[e._v(e._s(t.connection.name))])}),e._v(" "),n("close-session-dialog")],2)},staticRenderFns:[]};var q=null,z=null,A=null,B={},H={mounted:function(){z=this,bus.$off("activateTerminal"),bus.$on("activateTerminal",this.activateTerminal),bus.$off("removeTerminal"),bus.$on("removeTerminal",this.removeTerminal),bus.$off("terminalData"),bus.$on("terminalData",this.terminalData),window.removeEventListener("resize",this.windowResized),window.addEventListener("resize",this.windowResized)},beforeDestroy:function(){q&&(q.destroy(),q=null)},methods:{activateTerminal:function(e){o.default.nextTick(function(){if(z.$parent.$parent.showTerminal=!1,localStorage.currentSessionId=e.id,z.initTerminal(),q.fit(),A=e,"closed"!=e.state){var t={command:"activateSSH",sessionId:e.id,rows:q.rows,cols:q.cols};bus.$emit("socketSend",t)}else{t={command:"sendCacheSSH",sessionId:e.id,rows:q.rows,cols:q.cols};bus.$emit("socketSend",t)}setTimeout(function(){z.$parent.$parent.showTerminal=!0},150)})},initTerminal:function(){null==q&&(q=new Terminal({theme:{foreground:"#000",background:"#fafafa",cursor:"#000",selection:"rgba(0, 0, 0, 0.3)"},fontFamily:"Lucida Console,monospace",fontSize:16,cursorStyle:"block",cursorBlink:!0})).open(document.getElementById("terminalContainer")),q.off("data",this.terminalDataEntered),q.on("data",this.terminalDataEntered),q.off("key",this.terminalKeyPressed),q.on("key",this.terminalKeyPressed),q.focus(),q.reset(),setTimeout(function(){q.scrollToBottom()},150)},removeTerminal:function(){q&&(q.destroy(),q=null)},terminalDataEntered:function(e){var t={command:"keySSH",sessionId:A.id,key:e};bus.$emit("socketSend",t)},terminalKeyPressed:function(e){"closed"==A.state&&(13==e.charCodeAt(0)&&this.activateTerminal(A),27==e.charCodeAt(0)&&bus.$emit("removeSession",A))},terminalData:function(e){q.write(e.text)},windowResized:function(){q&&(clearTimeout(B),B=setTimeout(function(){q.fit();var e={command:"setPtySizeSSH",sessionId:A.id,rows:q.rows,cols:q.cols};bus.$emit("socketSend",e)},100))}}},L={render:function(){var e=this.$createElement;return(this._self._c||e)("div")},staticRenderFns:[]},N={components:{Sidebar:k,Session:x,Socket:R,NavSessionList:n("VU/8")(U,V,!1,function(e){n("8IKe")},"data-v-6bff64f8",null).exports,Terminal:n("VU/8")(H,L,!1,null,null,null).exports},data:function(){return{loadingSessions:!0,showTerminal:!1,hideDrawer:!1,sessions:[],connections:[]}},methods:{loadConnections:function(){var e=this;this.$http.get("/api/connection/get").then(function(t){e.connections=t.body})},logout:function(){bus.$emit("logout")}},mounted:function(){this.loadConnections(),bus.$off("loadConnections"),bus.$on("loadConnections",this.loadConnections)}},O={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-app",{class:{hideDrawer:e.hideDrawer},attrs:{id:"vapp"}},[n("v-navigation-drawer",{staticClass:"elevation-0",attrs:{permanent:"",app:"",light:""}},[n("sidebar")],1),e._v(" "),n("v-toolbar",{staticClass:"elevation-0",attrs:{dense:"",color:"blue-grey",dark:"",app:""}},[e.hideDrawer?n("v-btn",{staticClass:"elevation-0 mr-3",attrs:{flat:"",icon:"",small:""},on:{click:function(t){e.hideDrawer=!1}}},[n("v-icon",[e._v("arrow_right")])],1):e._e(),e._v(" "),n("navSessionList"),e._v(" "),n("v-btn",{attrs:{icon:""},on:{click:e.logout}},[n("v-icon",[e._v("exit_to_app")])],1)],1),e._v(" "),n("v-content",[n("v-container",{attrs:{fluid:"","fill-height":""}},[0!=e.sessions.length||e.loadingSessions?e._e():n("v-layout",{staticClass:"mb-5",attrs:{"align-center":"","justify-center":""}},[n("span",[e._v("There are no open sessions.")])]),e._v(" "),e.sessions.length>0?n("v-layout",{class:{showTerminal:e.showTerminal},attrs:{"d-flex":"",id:"terminalContainer"}}):e._e()],1)],1),e._v(" "),n("session"),e._v(" "),n("socket"),e._v(" "),n("terminal")],1)},staticRenderFns:[]};var j=n("VU/8")(N,O,!1,function(e){n("4xBg")},"data-v-90b25986",null).exports;o.default.use(c.a);var Z=new c.a({mode:"history",routes:[{path:"/",component:f},{path:"/home",component:j}]});Z.beforeEach(function(e,t,n){"/"!=e.path&&null==l.user?n({path:"/"}):n()});var M=Z,J={render:function(){var e=this.$createElement;return(this._self._c||e)("router-view")},staticRenderFns:[]},Q=n("VU/8")(null,J,!1,null,null,null).exports,W=(n("7zck"),n("Z5gD"),n("fIPj"),n("13sD")),G=n("6x4x"),X=n("AaoT");o.default.use(r.a),o.default.use(a.a),W.Terminal.applyAddon(G),W.Terminal.applyAddon(X),window.Terminal=W.Terminal,window.bus=new o.default,new o.default({el:"#app",router:M,components:{App:Q},template:"<v-app><App/></v-app>"})},Z5gD:function(e,t){},aNIP:function(e,t){},fIPj:function(e,t){},"oBZ/":function(e,t){}},["NHnr"]);