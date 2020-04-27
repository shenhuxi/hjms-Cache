!function(U,x,_){"use strict";var B="ht",c=U[B],r=null,g=Math,C=g.PI,d=g.cos,H=g.sin,X=g.abs,K=g.max,D=g.sqrt,$=1e-5,n=c.Default,I=n.def,o=n.startAnim,q=n.createMatrix,v=n.transformVec,i=n.getInternal(),z=i.addMethod,W=i.superCall,V=i.toPointsArray,b=i.createNormals,Y=i.toFloatArray,S=i.glMV,G=i.batchShape,a=i.createNodeMatrix,Q=i.getFaceInfo,s=i.transformAppend,E=i.drawFaceInfo,k=i.createAnim,t=i.cube(),P=t.is,l=t.vs,j=t.uv,u=i.ui(),F=c.Node,A=c.Shape,m="h",f="v",w="front",N="back",T="left",J="right",h="top",p="bottom",Z="dw.expanded",y=".expanded",O="dw.angle",L=".angle",M=function(k,z,V){I(B+"."+k,z,V)},R=function(d,x,J){J?d.push(x.x,x.y):d.push(x.x,x.y,x.z)},e=[1,0,0],oi=function(W,w,B,D){var Q,Y,t,U,L=0,k=[];if(D)for(Q=D.length;Q>L;L+=3)Y=D[L],t=D[L+1],U=D[L+2],k.push(new lb([new Oe([w[3*Y],w[3*Y+1],w[3*Y+2]],e,B?[B[2*Y],B[2*Y+1],0]:r),new Oe([w[3*t],w[3*t+1],w[3*t+2]],e,B?[B[2*t],B[2*t+1],0]:r),new Oe([w[3*U],w[3*U+1],w[3*U+2]],e,B?[B[2*U],B[2*U+1],0]:r)],W));else for(Q=w.length;Q>L;L+=3)Y=L,t=L+1,U=L+2,k.push(new lb([new Oe([w[3*Y],w[3*Y+1],w[3*Y+2]],e,B?[B[2*Y],B[2*Y+1],0]:r),new Oe([w[3*t],w[3*t+1],w[3*t+2]],e,B?[B[2*t],B[2*t+1],0]:r),new Oe([w[3*U],w[3*U+1],w[3*U+2]],e,B?[B[2*U],B[2*U+1],0]:r)],W));return k},Ro=[T,w,J,N,h,p],zq=Ro.concat("csg"),cq=[0,6,12,18,24,30],Kr=function(W,Z,$){for(var z=a(W),n=[],f=0;6>f;f++)for(var g=Ro[f],R=cq[f],G=$?Z.getFaceUv(W,g):r,C=$?Z.getFaceUvScale(W,g):r,V=$?Z.getFaceUvOffset(W,g):r,_=0;2>_;_++){var u,i,O,q=P[R+3*_],B=P[R+3*_+1],h=P[R+3*_+2];if($){if(G){var M=8*f;u=[G[2*q-M],G[2*q+1-M],0],i=[G[2*B-M],G[2*B+1-M],0],O=[G[2*h-M],G[2*h+1-M],0]}else u=[j[2*q],j[2*q+1],0],i=[j[2*B],j[2*B+1],0],O=[j[2*h],j[2*h+1],0];C&&(u[0]*=C[0],u[1]*=C[1],i[0]*=C[0],i[1]*=C[1],O[0]*=C[0],O[1]*=C[1]),V&&(u[0]+=V[0],u[1]+=V[1],i[0]+=V[0],i[1]+=V[1],O[0]+=V[0],O[1]+=V[1])}n.push(new lb([new Oe(v([l[3*q],l[3*q+1],l[3*q+2]],z),e,u),new Oe(v([l[3*B],l[3*B+1],l[3*B+2]],z),e,i),new Oe(v([l[3*h],l[3*h+1],l[3*h+2]],z),e,O)],W))}return of.$15n(n)},hg=function(W,T){var J,w=W.data.getAttaches();if(w&&w.each(function(r){r instanceof _b&&r.s("attach.operation")&&(J||(J=[]),J.push(r))}),J){var U;Ro.forEach(function(H){var R=oi(H,W[H].vs,W[H].tuv);U=U?U.concat(R):R}),U=of.$15n(U),J.forEach(function(l){var a=l.s("attach.operation");U[a]&&(U=U[a](Kr(l,W.gv,W.csg.tuv)))}),Ro.forEach(function(r){r=W[r],r.vs=[],r.tuv&&(r.tuv=[])}),U.$19n().forEach(function(T){var N=T.$10n;if(N instanceof _b){if(N.s("attach.cull"))return;N="csg"}for(var B=W[N],b=B.vs,J=B.tuv,m=T.$9n,F=2;F<m.length;F++)R(b,m[0].$24n),R(b,m[F-1].$24n),R(b,m[F].$24n),J&&(R(J,m[0].uv,!0),R(J,m[F-1].uv,!0),R(J,m[F].uv,!0))})}zq.forEach(function(q){var x=W[q];x.visible&&x.vs.length?(x.ns=b(x.vs),Y(x,"vs"),Y(x,"tuv")):delete W[q]}),T&&(G(W,r,T),W.clear())};z(n,{createFrameModel:function(Q,H,u,a){Q=Q==r?.07:Q,H=H==r?Q:H,u=u==r?Q:u,a=a?a:{};var E=a.top,j=a.bottom,F=a.left,l=a.right,h=a.front,g=a.back,O=[],C=[];return h===!0?(O.push(-.5,.5,.5,-.5,-.5,.5,.5,-.5,.5,.5,-.5,.5,.5,.5,.5,-.5,.5,.5),C.push(0,0,0,1,1,1,1,1,1,0,0,0)):h===!1||(O.push(-.5,.5,.5,-.5,-.5,.5,-.5+Q,-.5,.5,-.5+Q,-.5,.5,-.5+Q,.5,.5,-.5,.5,.5,.5-Q,.5,.5,.5-Q,-.5,.5,.5,-.5,.5,.5,-.5,.5,.5,.5,.5,.5-Q,.5,.5,-.5+Q,.5,.5,-.5+Q,.5-H,.5,.5-Q,.5-H,.5,.5-Q,.5-H,.5,.5-Q,.5,.5,-.5+Q,.5,.5,-.5+Q,-.5+H,.5,-.5+Q,-.5,.5,.5-Q,-.5,.5,.5-Q,-.5,.5,.5-Q,-.5+H,.5,-.5+Q,-.5+H,.5),C.push(0,0,0,1,Q,1,Q,1,Q,0,0,0,1-Q,0,1-Q,1,1,1,1,1,1,0,1-Q,0,Q,0,Q,H,1-Q,H,1-Q,H,1-Q,0,Q,0,Q,1-H,Q,1,1-Q,1,1-Q,1,1-Q,1-H,Q,1-H)),g===!0?(O.push(-.5,.5,-.5,.5,-.5,-.5,-.5,-.5,-.5,.5,-.5,-.5,-.5,.5,-.5,.5,.5,-.5),C.push(1,0,0,1,1,1,0,1,1,0,0,0)):g===!1||(O.push(-.5,.5,-.5,-.5+Q,-.5,-.5,-.5,-.5,-.5,-.5+Q,-.5,-.5,-.5,.5,-.5,-.5+Q,.5,-.5,.5-Q,.5,-.5,.5,-.5,-.5,.5-Q,-.5,-.5,.5,-.5,-.5,.5-Q,.5,-.5,.5,.5,-.5,-.5+Q,.5,-.5,.5-Q,.5-H,-.5,-.5+Q,.5-H,-.5,.5-Q,.5-H,-.5,-.5+Q,.5,-.5,.5-Q,.5,-.5,-.5+Q,-.5+H,-.5,.5-Q,-.5,-.5,-.5+Q,-.5,-.5,.5-Q,-.5,-.5,-.5+Q,-.5+H,-.5,.5-Q,-.5+H,-.5),C.push(1,0,1-Q,1,1,1,1-Q,1,1,0,1-Q,0,Q,0,0,1,Q,1,0,1,Q,0,0,0,1-Q,0,Q,H,1-Q,H,Q,H,1-Q,0,Q,0,1-Q,1-H,Q,1,1-Q,1,Q,1,1-Q,1-H,Q,1-H)),F===!0?(O.push(-.5,.5,-.5,-.5,-.5,-.5,-.5,-.5,.5,-.5,-.5,.5,-.5,.5,.5,-.5,.5,-.5),C.push(0,0,0,1,1,1,1,1,1,0,0,0)):F===!1||(O.push(-.5,.5,-.5,-.5,-.5,-.5,-.5,-.5,-.5+u,-.5,-.5,-.5+u,-.5,.5,-.5+u,-.5,.5,-.5,-.5,.5,.5-u,-.5,-.5,.5-u,-.5,-.5,.5,-.5,-.5,.5,-.5,.5,.5,-.5,.5,.5-u,-.5,.5,-.5+u,-.5,.5-H,-.5+u,-.5,.5-H,.5-u,-.5,.5-H,.5-u,-.5,.5,.5-u,-.5,.5,-.5+u,-.5,-.5+H,-.5+u,-.5,-.5,-.5+u,-.5,-.5,.5-u,-.5,-.5,.5-u,-.5,-.5+H,.5-u,-.5,-.5+H,-.5+u),C.push(0,0,0,1,u,1,u,1,u,0,0,0,1-u,0,1-u,1,1,1,1,1,1,0,1-u,0,u,0,u,H,1-u,H,1-u,H,1-u,0,u,0,u,1-H,u,1,1-u,1,1-u,1,1-u,1-H,u,1-H)),l===!0?(O.push(.5,.5,-.5,.5,-.5,.5,.5,-.5,-.5,.5,-.5,.5,.5,.5,-.5,.5,.5,.5),C.push(1,0,0,1,1,1,0,1,1,0,0,0)):l===!1||(O.push(.5,.5,-.5,.5,-.5,-.5+u,.5,-.5,-.5,.5,-.5,-.5+u,.5,.5,-.5,.5,.5,-.5+u,.5,.5,.5-u,.5,-.5,.5,.5,-.5,.5-u,.5,-.5,.5,.5,.5,.5-u,.5,.5,.5,.5,.5,-.5+u,.5,.5-H,.5-u,.5,.5-H,-.5+u,.5,.5-H,.5-u,.5,.5,-.5+u,.5,.5,.5-u,.5,-.5+H,-.5+u,.5,-.5,.5-u,.5,-.5,-.5+u,.5,-.5,.5-u,.5,-.5+H,-.5+u,.5,-.5+H,.5-u),C.push(1,0,1-u,1,1,1,1-u,1,1,0,1-u,0,u,0,0,1,u,1,0,1,u,0,0,0,1-u,0,u,H,1-u,H,u,H,1-u,0,u,0,1-u,1-H,u,1,1-u,1,u,1,1-u,1-H,u,1-H)),E===!0?(O.push(.5,.5,.5,.5,.5,-.5,-.5,.5,-.5,-.5,.5,-.5,-.5,.5,.5,.5,.5,.5),C.push(1,1,1,0,0,0,0,0,0,1,1,1)):E===!1||(O.push(.5,.5,.5,.5,.5,-.5,.5-Q,.5,-.5,.5-Q,.5,-.5,.5-Q,.5,.5,.5,.5,.5,-.5+Q,.5,.5,-.5+Q,.5,-.5,-.5,.5,-.5,-.5,.5,-.5,-.5,.5,.5,-.5+Q,.5,.5,.5-Q,.5,.5,.5-Q,.5,.5-u,-.5+Q,.5,.5-u,-.5+Q,.5,.5-u,-.5+Q,.5,.5,.5-Q,.5,.5,.5-Q,.5,-.5+u,.5-Q,.5,-.5,-.5+Q,.5,-.5,-.5+Q,.5,-.5,-.5+Q,.5,-.5+u,.5-Q,.5,-.5+u),C.push(1,1,1,0,1-Q,0,1-Q,0,1-Q,1,1,1,Q,1,Q,0,0,0,0,0,0,1,Q,1,1-Q,1,1-Q,1-u,Q,1-u,Q,1-u,Q,1,1-Q,1,1-Q,u,1-Q,0,Q,0,Q,0,Q,u,1-Q,u)),j===!0?(O.push(.5,-.5,.5,-.5,-.5,-.5,.5,-.5,-.5,-.5,-.5,-.5,.5,-.5,.5,-.5,-.5,.5),C.push(1,0,0,1,1,1,0,1,1,0,0,0)):j===!1||(O.push(.5,-.5,.5,.5-Q,-.5,-.5,.5,-.5,-.5,.5-Q,-.5,-.5,.5,-.5,.5,.5-Q,-.5,.5,-.5+Q,-.5,.5,-.5,-.5,-.5,-.5+Q,-.5,-.5,-.5,-.5,-.5,-.5+Q,-.5,.5,-.5,-.5,.5,.5-Q,-.5,.5,-.5+Q,-.5,.5-u,.5-Q,-.5,.5-u,-.5+Q,-.5,.5-u,.5-Q,-.5,.5,-.5+Q,-.5,.5,.5-Q,-.5,-.5+u,-.5+Q,-.5,-.5,.5-Q,-.5,-.5,-.5+Q,-.5,-.5,.5-Q,-.5,-.5+u,-.5+Q,-.5,-.5+u),C.push(1,0,1-Q,1,1,1,1-Q,1,1,0,1-Q,0,Q,0,0,1,Q,1,0,1,Q,0,0,0,1-Q,0,Q,u,1-Q,u,Q,u,1-Q,0,Q,0,1-Q,1-u,Q,1,1-Q,1,Q,1,1-Q,1-u,Q,1-u)),{vs:O,uv:C}}}),z(c.Style,{"dw.flip":!1,"dw.s3":[.999,.999,.5],"dw.t3":_,"dw.expanded":!1,"dw.toggleable":!0,"dw.axis":"left","dw.start":0,"dw.end":C/2,"dw.angle":0,"attach.cull":!1,"attach.operation":"subtract"},!0),Ro.forEach(function(a){var k={};k[a+y]=!1,k[a+".toggleable"]=!1,k[a+".axis"]=T,k[a+".start"]=0,k[a+".end"]=C/2,k[a+L]=0,z(c.Style,k,!0)});var of=function(){this.$4n=[]};of.$15n=function(H){var I=new of;return I.$4n=H,I},of.prototype={clone:function(){var m=new of;return m.$4n=this.$4n.map(function(x){return x.clone()}),m},$19n:function(){return this.$4n},union:function(U){var M=new Ed(this.clone().$4n),V=new Ed(U.clone().$4n);return M.$3n(V),V.$3n(M),V.$6n(),V.$3n(M),V.$6n(),M.$7n(V.$2n()),of.$15n(M.$2n())},subtract:function(y){var I=new Ed(this.clone().$4n),Q=new Ed(y.clone().$4n);return I.$6n(),I.$3n(Q),Q.$3n(I),Q.$6n(),Q.$3n(I),Q.$6n(),I.$7n(Q.$2n()),I.$6n(),of.$15n(I.$2n())},intersect:function(e){var Q=new Ed(this.clone().$4n),o=new Ed(e.clone().$4n);return Q.$6n(),o.$3n(Q),o.$6n(),Q.$3n(o),o.$3n(Q),Q.$7n(o.$2n()),Q.$6n(),of.$15n(Q.$2n())},inverse:function(){var M=this.clone();return M.$4n.map(function(c){c.flip()}),M}},of.cube=function(s){s=s||{};var d=new rq(s.center||[0,0,0]),m=s.radius?s.radius.length?s.radius:[s.radius,s.radius,s.radius]:[1,1,1];return of.$15n([[[0,4,6,2],[-1,0,0]],[[1,3,7,5],[1,0,0]],[[0,1,5,4],[0,-1,0]],[[2,6,7,3],[0,1,0]],[[0,2,3,1],[0,0,-1]],[[4,5,7,6],[0,0,1]]].map(function(R){return new lb(R[0].map(function(V){var q=new rq(d.x+m[0]*(2*!!(1&V)-1),d.y+m[1]*(2*!!(2&V)-1),d.z+m[2]*(2*!!(4&V)-1));return new Oe(q,new rq(R[1]))}))}))},of.sphere=function(f){function E(S,k){S*=2*C,k*=C;var z=new rq(d(S)*H(k),d(k),H(S)*H(k));b.push(new Oe(r.$20n(z.$21n(O)),z))}f=f||{};for(var b,r=new rq(f.center||[0,0,0]),O=f.radius||1,k=f.slices||16,i=f.stacks||8,c=[],D=0;k>D;D++)for(var P=0;i>P;P++)b=[],E(D/k,P/i),P>0&&E((D+1)/k,P/i),i-1>P&&E((D+1)/k,(P+1)/i),E(D/k,(P+1)/i),c.push(new lb(b));return of.$15n(c)},of.cylinder=function(G){function Y(f,U,h){var n=2*U*C,D=b.$21n(d(n)).$20n(I.$21n(H(n))),v=R.$20n(s.$21n(f)).$20n(D.$21n(p)),Z=D.$21n(1-X(h)).$20n(F.$21n(h));return new Oe(v,Z)}G=G||{};for(var R=new rq(G.start||[0,-1,0]),m=new rq(G.end||[0,1,0]),s=m.$13n(R),p=G.radius||1,P=G.slices||16,F=s.$14n(),Z=X(F.y)>.5,b=new rq(Z,!Z,0).$12n(F).$14n(),I=b.$12n(F).$14n(),k=new Oe(R,F.$11n()),t=new Oe(m,F.$14n()),M=[],c=0;P>c;c++){var f=c/P,h=(c+1)/P;M.push(new lb([k,Y(0,f,-1),Y(0,h,-1)])),M.push(new lb([Y(0,h,0),Y(0,f,0),Y(1,f,0),Y(1,h,0)])),M.push(new lb([t,Y(1,h,1),Y(1,f,1)]))}return of.$15n(M)};var rq=function(S,c,Q){var M=this;3==arguments.length?(M.x=S,M.y=c,M.z=Q):"x"in S?(M.x=S.x,M.y=S.y,M.z=S.z):(M.x=S[0],M.y=S[1],M.z=S[2])};rq.prototype={clone:function(){return new rq(this.x,this.y,this.z)},$11n:function(){return new rq(-this.x,-this.y,-this.z)},$20n:function(A){return new rq(this.x+A.x,this.y+A.y,this.z+A.z)},$13n:function(C){return new rq(this.x-C.x,this.y-C.y,this.z-C.z)},$21n:function(y){return new rq(this.x*y,this.y*y,this.z*y)},$16n:function(O){return new rq(this.x/O,this.y/O,this.z/O)},$23n:function(k){return this.x*k.x+this.y*k.y+this.z*k.z},lerp:function(k,v){return this.$20n(k.$13n(this).$21n(v))},length:function(){return D(this.$23n(this))},$14n:function(){return this.$16n(this.length())},$12n:function(L){var o=this;return new rq(o.y*L.z-o.z*L.y,o.z*L.x-o.x*L.z,o.x*L.y-o.y*L.x)}};var Oe=function(B,v,k){var m=this;m.$24n=new rq(B),m.$22n=new rq(v),m.uv=k?new rq(k):null};Oe.prototype={clone:function(){var N=this;return new Oe(N.$24n.clone(),N.$22n.clone(),N.uv?N.uv.clone():null)},flip:function(){this.$22n=this.$22n.$11n()},$18n:function(I,J){var T=this;return new Oe(T.$24n.lerp(I.$24n,J),T.$22n.lerp(I.$22n,J),T.uv?T.uv.lerp(I.uv,J):null)}};var Ak=function(N,b){this.$22n=N,this.w=b};Ak.$17n=function(N,f,S){var i=f.$13n(N).$12n(S.$13n(N)).$14n();return new Ak(i,i.$23n(N))},Ak.prototype={clone:function(){return new Ak(this.$22n.clone(),this.w)},flip:function(){var q=this;q.$22n=q.$22n.$11n(),q.w=-q.w},$5n:function(k,Y,C,s,K){for(var g=this,U=0,B=1,R=2,t=3,T=0,M=[],H=0;H<k.$9n.length;H++){var J=g.$22n.$23n(k.$9n[H].$24n)-g.w,l=-$>J?R:J>$?B:U;T|=l,M.push(l)}switch(T){case U:(g.$22n.$23n(k.$8n.$22n)>0?Y:C).push(k);break;case B:s.push(k);break;case R:K.push(k);break;case t:for(var o=[],h=[],H=0;H<k.$9n.length;H++){var _=(H+1)%k.$9n.length,S=M[H],w=M[_],v=k.$9n[H],m=k.$9n[_];if(S!=R&&o.push(v),S!=B&&h.push(S!=R?v.clone():v),(S|w)==t){var J=(g.w-this.$22n.$23n(v.$24n))/g.$22n.$23n(m.$24n.$13n(v.$24n)),Q=v.$18n(m,J);o.push(Q),h.push(Q.clone())}}o.length>=3&&s.push(new lb(o,k.$10n)),h.length>=3&&K.push(new lb(h,k.$10n))}}};var lb=function(A,o){var i=this;i.$9n=A,i.$10n=o,i.$8n=Ak.$17n(A[0].$24n,A[1].$24n,A[2].$24n)};lb.prototype={clone:function(){var e=this.$9n.map(function(M){return M.clone()});return new lb(e,this.$10n)},flip:function(){this.$9n.reverse().map(function(c){c.flip()}),this.$8n.flip()}};var Ed=function(Y){var E=this;E.$8n=null,E.front=null,E.back=null,E.$4n=[],Y&&E.$7n(Y)};Ed.prototype={clone:function(){var i=this,Z=new Ed;return Z.$8n=i.$8n&&i.$8n.clone(),Z.front=i.front&&i.front.clone(),Z.back=i.back&&i.back.clone(),Z.$4n=i.$4n.map(function(D){return D.clone()}),Z},$6n:function(){for(var L=this,k=0;k<L.$4n.length;k++)L.$4n[k].flip();L.$8n.flip(),L.front&&L.front.$6n(),L.back&&L.back.$6n();var o=L.front;L.front=L.back,L.back=o},$1n:function(V){var r=this;if(!r.$8n)return V.slice();for(var Y=[],J=[],Z=0;Z<V.length;Z++)r.$8n.$5n(V[Z],Y,J,Y,J);return r.front&&(Y=r.front.$1n(Y)),J=r.back?r.back.$1n(J):[],Y.concat(J)},$3n:function(x){var m=this;m.$4n=x.$1n(m.$4n),m.front&&m.front.$3n(x),m.back&&m.back.$3n(x)},$2n:function(){var _=this,F=_.$4n.slice();return _.front&&(F=F.concat(_.front.$2n())),_.back&&(F=F.concat(_.back.$2n())),F},$7n:function(o){var f=this;if(o.length){f.$8n||(f.$8n=o[0].$8n.clone());for(var d=[],R=[],i=0;i<o.length;i++)this.$8n.$5n(o[i],f.$4n,f.$4n,d,R);d.length&&(f.front||(f.front=new Ed),this.front.$7n(d)),R.length&&(f.back||(f.back=new Ed),f.back.$7n(R))}}};var Vh="symbol",Vj=c.Symbol=function(h,o,s){var w=this;W(Vj,w),w.s3(20,20,20),w.s({"all.visible":!1,shape:"rect"}),w.setIcon(h,o,s)};M("Symbol",F,{setIcon:function(l,T,x){var L,J=this;return Vj.superClass.setIcon.call(J,l),l?(L={for3d:!0,face:"center",position:44,names:[l]},x&&(L.transaprent=!0),T&&(L.autorotate=T),J.addStyleIcon(Vh,L)):J.removeStyleIcon(Vh),J.setWidth(i.getImageWidth(n.getImage(l),J)||20),L}});var _b=c.CSGNode=function(){W(_b,this),this.s({shape:"rect","attach.thickness":1.001})},bs={position:1,width:1,height:1,rotation:1,rotationX:1,rotationZ:1,rotationMode:1,tall:1,elevation:1,"s:attach.cull":1,"s:attach.operation":1};M("CSGNode",F,{_22Q:function(){return wg},onPropertyChanged:function(B){var A=this,N=A.getHost();_b.superClass.onPropertyChanged.call(A,B),bs[B.property]&&(N instanceof sn||N instanceof _b)&&N.fp("csgNodeChange",!0,!1)}});var wg=function(Z,j){W(wg,this,[Z,j])};I(wg,u.Node3dUI,{_80o:function(e,r,J){var A=this;A._shape3d?wg.superClass._80o.call(A,e,r,J):(S(A.gv),zq.forEach(function(c){E(A,e,r,A[c],J)}))},validate:function(e,p){var L=this,$=L.gv,n=L.data;if(n.s("shape3d"))return wg.superClass.validate.call(L,e,p),L._shape3d=!0,void 0;L._shape3d=!1;var U=a(n,$.getMat(n)),i=e&&e.uv;L.vf2("csg",i);for(var v=0;6>v;v++)for(var N=Ro[v],h=cq[v],d=L.vf2(N,i,p),c=d.mat||U,E=d.vs,Z=d.uv,S=d.tuv,x=0;2>x;x++){var o=P[h+3*x],t=P[h+3*x+1],C=P[h+3*x+2];if(s(E,c,[l[3*o],l[3*o+1],l[3*o+2]]),s(E,c,[l[3*t],l[3*t+1],l[3*t+2]]),s(E,c,[l[3*C],l[3*C+1],l[3*C+2]]),S)if(Z){var K=8*v;S.push(Z[2*o-K],Z[2*o+1-K],Z[2*t-K],Z[2*t+1-K],Z[2*C-K],Z[2*C+1-K])}else S.push(j[2*o],j[2*o+1],j[2*t],j[2*t+1],j[2*C],j[2*C+1])}hg(L,e,p)},vf2:function(L,Y,A){var f,E=this,m=E.gv.getFaceVisible(E.data,L);return f=Q(E,L,A),f.vs=[],f.tuv=m&&(f.texture||Y)?[]:r,f.visible=m,f}});var sn=c.CSGShape=function(){var b=this;W(sn,b),b.s({"shape.background":r,"shape.border.width":8}),b.setTall(240),b.setElevation(120),b.setThickness(14)};M("CSGShape",A,{IRotatable:!1,_22Q:function(){return yn},setRotationX:function(){},setRotation:function(){},setRotationZ:function(){},setSegments:function(){}});var yn=function(p,S){W(yn,this,[p,S])};I(yn,u.Shape3dUI,{_80o:function($,B,D){var M=this;M.undrawable||(S(M.gv),zq.forEach(function(T){E(M,$,B,M[T],D)}))},validate:function(I,H){var S=this,Q=S.data,s=Q.getPoints();if(S.undrawable=s.size()<2)return S.clear(),void 0;var A=Q.isClosePath(),U=K(Q._thickness/2,$),k=V(s,r,r,A);zq.forEach(function(q){S.vf(q,I&&I.uv,!0,H)}),A&&(S.left.visible=!1,S.right.visible=!1),S._12O(k,U),S._20Q(k),hg(S,I,H)}});var km=c.DoorWindow=function(){var l=this;W(km,l),l.setElevation(100),l.s3(100,200,14)};M("DoorWindow",_b,{IDoorWindow:!0,toggle:function(y){this.setExpanded(!this.s(Z),y)},isExpanded:function(){return this.s(Z)},setExpanded:function(G,E){var n=this,w=n.$25n,N=n.getDataModel(),u=n.s(Z);if(w&&(w.stop(!0),delete n.$25n),u!==G){N&&N.beginTransaction();var H=G?n.s("dw.end"):n.s("dw.start");n.s(Z,G),E=k(E),E?(u=n.s(O),E.action=function(j){n.s(O,u+(H-u)*j)},E.finishFunc=function(){N&&N.endTransaction()},n.$25n=o(E)):(n.s(O,H),N&&N.endTransaction())}},getMat:function(){var i=this,o=i.s("dw.s3"),l=i.s("dw.t3"),X=i.s("dw.flip"),U=i.s(O);if(o||U||l||X){var A=[];if(X&&A.push({r3:[0,C,0]}),o&&A.push({s3:o}),U){o=i.s3();var g=i.s("dw.axis"),b=o[0]/2,x=o[1]/2;o[2]/2,g===T?A.push({t3:[b,0,0]},{r3:[0,-U,0]},{t3:[-b,0,0]}):g===J?A.push({t3:[-b,0,0]},{r3:[0,U,0]},{t3:[b,0,0]}):g===h?A.push({t3:[0,-x,0]},{r3:[-U,0,0]},{t3:[0,x,0]}):g===p?A.push({t3:[0,x,0]},{r3:[U,0,0]},{t3:[0,-x,0]}):g===f?A.push({r3:[0,U,0]}):g===m&&A.push({r3:[U,0,0]})}return l&&A.push({t3:l}),q(A)}return r}});var Bo=c.CSGBox=function(){var g=this;W(Bo,g),g.setElevation(100),g.s3(100,200,100)};M("CSGBox",_b,{ICSGBox:!0,toggleFace:function(R,b){this.setFaceExpanded(R,!this.s(R+y),b)},isFaceExpanded:function(k){return this.s(k+y)},setFaceExpanded:function(U,i,v){var H=this,A=H.$25n,_=H.s(U+y);if(A&&(A.stop(!0),delete H.$25n),_!==i){var O=i?H.s(U+".end"):H.s(U+".start");H.s(U+y,i),v=k(v),v?(_=H.s(U+L),v.action=function(V){H.s(U+L,_+(O-_)*V)},H.$25n=o(v)):H.s(U+L,O)}},getFaceMat:function(I){var j=this,R=j.s(I+L);if(!R)return r;var K=j.s(I+".axis"),F=j.s3(),W=F[0]/2,b=F[1]/2,S=F[2]/2,U=[];return I===w&&(K===T?U.push({t3:[W,0,-S]},{r3:[0,-R,0]},{t3:[-W,0,S]}):K===J?U.push({t3:[-W,0,-S]},{r3:[0,R,0]},{t3:[W,0,S]}):K===h?U.push({t3:[0,-b,-S]},{r3:[-R,0,0]},{t3:[0,b,S]}):K===p?U.push({t3:[0,b,-S]},{r3:[R,0,0]},{t3:[0,-b,S]}):K===f?U.push({t3:[0,0,-S]},{r3:[0,R,0]},{t3:[0,0,S]}):K===m&&U.push({t3:[0,0,-S]},{r3:[R,0,0]},{t3:[0,0,S]})),I===N&&(K===T?U.push({t3:[-W,0,S]},{r3:[0,-R,0]},{t3:[W,0,-S]}):K===J?U.push({t3:[W,0,S]},{r3:[0,R,0]},{t3:[-W,0,-S]}):K===h?U.push({t3:[0,-b,S]},{r3:[R,0,0]},{t3:[0,b,-S]}):K===p?U.push({t3:[0,b,S]},{r3:[-R,0,0]},{t3:[0,-b,-S]}):K===f?U.push({t3:[0,0,S]},{r3:[0,R,0]},{t3:[0,0,-S]}):K===m&&U.push({t3:[0,0,S]},{r3:[R,0,0]},{t3:[0,0,-S]})),I===T&&(K===T?U.push({t3:[W,0,S]},{r3:[0,-R,0]},{t3:[-W,0,-S]}):K===J?U.push({t3:[W,0,-S]},{r3:[0,R,0]},{t3:[-W,0,S]}):K===h?U.push({t3:[W,-b,0]},{r3:[0,0,-R]},{t3:[-W,b,0]}):K===p?U.push({t3:[W,b,0]},{r3:[0,0,R]},{t3:[-W,-b,0]}):K===f?U.push({t3:[W,0,0]},{r3:[0,R,0]},{t3:[-W,0,0]}):K===m&&U.push({t3:[W,0,0]},{r3:[0,0,R]},{t3:[-W,0,0]})),I===J&&(K===T?U.push({t3:[-W,0,-S]},{r3:[0,-R,0]},{t3:[W,0,S]}):K===J?U.push({t3:[-W,0,S]},{r3:[0,R,0]},{t3:[W,0,-S]}):K===h?U.push({t3:[-W,-b,0]},{r3:[0,0,R]},{t3:[W,b,0]}):K===p?U.push({t3:[-W,b,0]},{r3:[0,0,-R]},{t3:[W,-b,0]}):K===f?U.push({t3:[-W,0,0]},{r3:[0,R,0]},{t3:[W,0,0]}):K===m&&U.push({t3:[-W,0,0]},{r3:[0,0,R]},{t3:[W,0,0]})),I===h&&(K===T?U.push({t3:[W,-b,0]},{r3:[0,0,R]},{t3:[-W,b,0]}):K===J?U.push({t3:[-W,-b,0]},{r3:[0,0,-R]},{t3:[W,b,0]}):K===h?U.push({t3:[0,-b,S]},{r3:[-R,0,0]},{t3:[0,b,-S]}):K===p?U.push({t3:[0,-b,-S]},{r3:[R,0,0]},{t3:[0,b,S]}):K===f?U.push({t3:[0,-b,0]},{r3:[0,0,R]},{t3:[0,b,0]}):K===m&&U.push({t3:[0,-b,0]},{r3:[R,0,0]},{t3:[0,b,0]})),I===p&&(K===T?U.push({t3:[W,b,0]},{r3:[0,0,-R]},{t3:[-W,-b,0]}):K===J?U.push({t3:[-W,b,0]},{r3:[0,0,R]},{t3:[W,-b,0]}):K===h?U.push({t3:[0,b,-S]},{r3:[-R,0,0]},{t3:[0,-b,S]}):K===p?U.push({t3:[0,b,S]},{r3:[R,0,0]},{t3:[0,-b,-S]}):K===f?U.push({t3:[0,b,0]},{r3:[0,0,R]},{t3:[0,-b,0]}):K===m&&U.push({t3:[0,b,0]},{r3:[R,0,0]},{t3:[0,-b,0]})),q(U)}})}("undefined"!=typeof global?global:"undefined"!=typeof self?self:"undefined"!=typeof window?window:(0,eval)("this"),Object);