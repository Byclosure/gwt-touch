package us.synx.wc.client.ui;

/**
 // Copyright (c) 2010 by John Beaven
 *
 // This program is free software: you can redistribute it and/or modify
 // it under the terms of the GNU Lesser General Public License as published by
 // the Free Software Foundation, either version 3 of the License, or
 // (at your option) any later version.
 *
 // This program is distributed in the hope that it will be useful,
 // but WITHOUT ANY WARRANTY; without even the implied warranty of
 // MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 // GNU General Public License for more details.
 *
 // You should have received a copy of the GNU Lesser General Public License
 // along with this program.  If not, see <http://www.gnu.org/licenses/>.
 // 
 // More details available at http://code.google.com/p/gwt-iscroll/
 *
 // Note: The original iScroll Javascript library is available from:
 // http://cubiq.org/, and is copyright (c) 2009 Matteo Spinelli 
 // released under the MIT license
 */

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class IPhoneScroller {

	public void refresh() {

		refresh(iScrollObj);
	}

	public void scrollTo(int position, int time_ms) {

		scrollTo(iScrollObj, String.valueOf(position), String.valueOf(time_ms)
				+ "ms");
	}

	public void scrollTo(int position) {

		scrollTo(iScrollObj, String.valueOf(position), "300ms");
	}

	public int getPosition() {

		return getPosition(iScrollObj);
	}

	public void setPosition(int pos) {

		setPosition(iScrollObj, pos);
	}

	JavaScriptObject iScrollObj;

	private static boolean libIncluded = false;

	public static class IPhoneScrollerConfig extends JavaScriptObject {

		protected IPhoneScrollerConfig() {
		}

		public final native static IPhoneScrollerConfig getDefault() /*-{
			return {};
		}-*/;

		public final native void setDesktopCompatibility(boolean b) /*-{
			this.desktopCompatibility = b;
		}-*/;

		public final native void setBounceLock(boolean b) /*-{
			this.bounceLock = b;
		}-*/;

		public final native void setSnap(boolean b) /*-{
			this.snap = b;
		}-*/;

		public final native void setMomentum(boolean b) /*-{
			this.momentum = b;
		}-*/;

		public final native void setHScroll(boolean b) /*-{
			this.hScroll = b;
		}-*/;

		public final native void setHScrollbar(boolean b) /*-{
			this.hScrollbar = b;
		}-*/;

		public final native void setVScroll(boolean b) /*-{
			this.vScroll = b;
		}-*/;

		public final native void setVScrollbar(boolean b) /*-{
			this.vScrollbar = b;
		}-*/;
	}

	public IPhoneScroller(Widget w) {
		this(w, null);
	}

	public IPhoneScroller(Widget w, IPhoneScrollerConfig config) {

		if (!libIncluded) {

			includeLibJSNI();
			libIncluded = true;
		}

		if (config == null) {
			config = IPhoneScrollerConfig.getDefault();
		}
		// always setting this one
		config.setDesktopCompatibility(true);

		iScrollObj = makeScrollableJSNI(w.getElement(), config);
	}

	protected native final void refresh(JavaScriptObject o) /*-{
		o.refresh();
	}-*/;

	protected native final void scrollTo(JavaScriptObject o, String position,
			String time_ms) /*-{
		o.scrollTo(position, time_ms);
	}-*/;

	protected native final int getPosition(JavaScriptObject o) /*-{
		return o.position();
	}-*/;

	protected native final void setPosition(JavaScriptObject o, int pos) /*-{
		o.setposition(pos);
	}-*/;

	protected native final JavaScriptObject makeScrollableJSNI(Element elem,
			IPhoneScrollerConfig config) /*-{
		return new $wnd.iScroll(elem, config);
	}-*/;

	protected native final void includeLibJSNI() /*-{
		// INCLUSION OF THE ORIGINAL iScroll LIBRARY..
		//
		// Find more about the scrolling function at
		// http://cubiq.org/iscroll
		// Copyright (c) 2010 Matteo Spinelli, http://cubiq.org/
		// Released under MIT license
		// http://cubiq.org/dropbox/mit-license.txt
		// 
		// Version 3.7.1 - Last updated: 2010.10.08
		(function() {
			function j(n, l) {
				var o = this, m;
				o.element = typeof n == "object" ? n : $doc.getElementById(n);
				o.wrapper = o.element.parentNode;
				o.element.style.webkitTransitionProperty = "-webkit-transform";
				o.element.style.webkitTransitionTimingFunction = "cubic-bezier(0,0,0.25,1)";
				o.element.style.webkitTransitionDuration = "0";
				o.element.style.webkitTransform = h + "0,0" + b;
				o.options = {
					hScroll : true,
					vScroll : true,
					bounce : d,
					momentum : d,
					checkDOMChanges : true,
					topOnDOMChanges : false,
					hScrollbar : d,
					vScrollbar : d,
					fadeScrollbar : g || !a,
					shrinkScrollbar : g || !a,
					desktopCompatibility : false,
					overflow : "auto",
					snap : false,
					bounceLock : false,
					scrollbarColor : "rgba(0,0,0,0.5)",
					onScrollEnd : function() {
					}
				};
				if (typeof l == "object") {
					for (m in l) {
						o.options[m] = l[m]
					}
				}
				if (o.options.desktopCompatibility) {
					o.options.overflow = "hidden"
				}
				o.onScrollEnd = o.options.onScrollEnd;
				delete o.options.onScrollEnd;
				o.wrapper.style.overflow = o.options.overflow;
				o.refresh();
				$wnd.addEventListener(
						"onorientationchange" in window ? "orientationchange"
								: "resize", o, false);
				if (a || o.options.desktopCompatibility) {
					o.element.addEventListener(f, o, false);
					o.element.addEventListener(i, o, false);
					o.element.addEventListener(e, o, false)
				}
				if (o.options.checkDOMChanges) {
					o.element.addEventListener("DOMSubtreeModified", o, false)
				}
			}
			j.prototype = {
				x : 0,
				y : 0,
				enabled : true,
				handleEvent : function(m) {
					var l = this;
					switch (m.type) {
					case f:
						l.touchStart(m);
						break;
					case i:
						l.touchMove(m);
						break;
					case e:
						l.touchEnd(m);
						break;
					case "webkitTransitionEnd":
						l.transitionEnd();
						break;
					case "orientationchange":
					case "resize":
						l.refresh();
						break;
					case "DOMSubtreeModified":
						l.onDOMModified(m);
						break
					}
				},
				onDOMModified : function(m) {
					var l = this;
					if (m.target.parentNode != l.element) {
						return

					}
					setTimeout(function() {
						l.refresh()
					}, 0);
					if (l.options.topOnDOMChanges && (l.x != 0 || l.y != 0)) {
						l.scrollTo(0, 0, "0")
					}
				},
				refresh : function() {
					var m = this, o = m.x, n = m.y, l;
					m.scrollWidth = m.wrapper.clientWidth;
					m.scrollHeight = m.wrapper.clientHeight;
					m.scrollerWidth = m.element.offsetWidth;
					m.scrollerHeight = m.element.offsetHeight;
					m.maxScrollX = m.scrollWidth - m.scrollerWidth;
					m.maxScrollY = m.scrollHeight - m.scrollerHeight;
					m.directionX = 0;
					m.directionY = 0;
					if (m.scrollX) {
						if (m.maxScrollX >= 0) {
							o = 0
						} else {
							if (m.x < m.maxScrollX) {
								o = m.maxScrollX
							}
						}
					}
					if (m.scrollY) {
						if (m.maxScrollY >= 0) {
							n = 0
						} else {
							if (m.y < m.maxScrollY) {
								n = m.maxScrollY
							}
						}
					}
					if (m.options.snap) {
						m.maxPageX = -Math.floor(m.maxScrollX / m.scrollWidth);
						m.maxPageY = -Math.floor(m.maxScrollY / m.scrollHeight);
						l = m.snap(o, n);
						o = l.x;
						n = l.y
					}
					if (o != m.x || n != m.y) {
						m.setTransitionTime("0");
						m.setPosition(o, n, true)
					}
					m.scrollX = m.scrollerWidth > m.scrollWidth;
					m.scrollY = !m.options.bounceLock && !m.scrollX
							|| m.scrollerHeight > m.scrollHeight;
					if (m.options.hScrollbar && m.scrollX) {
						m.scrollBarX = m.scrollBarX
								|| new k("horizontal", m.wrapper,
										m.options.fadeScrollbar,
										m.options.shrinkScrollbar,
										m.options.scrollbarColor);
						m.scrollBarX.init(m.scrollWidth, m.scrollerWidth)
					} else {
						if (m.scrollBarX) {
							m.scrollBarX = m.scrollBarX.remove()
						}
					}
					if (m.options.vScrollbar && m.scrollY
							&& m.scrollerHeight > m.scrollHeight) {
						m.scrollBarY = m.scrollBarY
								|| new k("vertical", m.wrapper,
										m.options.fadeScrollbar,
										m.options.shrinkScrollbar,
										m.options.scrollbarColor);
						m.scrollBarY.init(m.scrollHeight, m.scrollerHeight)
					} else {
						if (m.scrollBarY) {
							m.scrollBarY = m.scrollBarY.remove()
						}
					}
				},
				setPosition : function(l, o, n) {
					var m = this;
					m.x = m.options.hScroll ? l : 0;
					m.y = m.options.vScroll ? o : 0;
					m.element.style.webkitTransform = h + m.x + "px," + m.y
							+ "px" + b;
					if (!n) {
						if (m.scrollBarX) {
							m.scrollBarX.setPosition(m.x)
						}
						if (m.scrollBarY) {
							m.scrollBarY.setPosition(m.y)
						}
					}
				},
				setTransitionTime : function(m) {
					var l = this;
					m = m || "0";
					l.element.style.webkitTransitionDuration = m;
					if (l.scrollBarX) {
						l.scrollBarX.bar.style.webkitTransitionDuration = m;
						l.scrollBarX.wrapper.style.webkitTransitionDuration = d
								&& l.options.fadeScrollbar ? "300ms" : "0"
					}
					if (l.scrollBarY) {
						l.scrollBarY.bar.style.webkitTransitionDuration = m;
						l.scrollBarY.wrapper.style.webkitTransitionDuration = d
								&& l.options.fadeScrollbar ? "300ms" : "0"
					}
				},
				touchStart : function(n) {
					var m = this, l;
					if (!m.enabled) {
						return

					}
					n.preventDefault();
					n.stopPropagation();
					m.scrolling = true;
					m.moved = false;
					m.distX = 0;
					m.distY = 0;
					m.setTransitionTime("0");
					if (m.options.momentum || m.options.snap) {
						l = new WebKitCSSMatrix($wnd
								.getComputedStyle(m.element).webkitTransform);
						if (l.e != m.x || l.f != m.y) {
							$doc.removeEventListener("webkitTransitionEnd", m,
									false);
							m.setPosition(l.e, l.f);
							m.moved = true
						}
					}
					m.touchStartX = a ? n.changedTouches[0].pageX : n.pageX;
					m.scrollStartX = m.x;
					m.touchStartY = a ? n.changedTouches[0].pageY : n.pageY;
					m.scrollStartY = m.y;
					m.scrollStartTime = n.timeStamp;
					m.directionX = 0;
					m.directionY = 0
				},
				touchMove : function(r) {
					if (!this.scrolling) {
						return

					}
					var p = this, o = a ? r.changedTouches[0].pageX : r.pageX, n = a ? r.changedTouches[0].pageY
							: r.pageY, m = p.scrollX ? o - p.touchStartX : 0, l = p.scrollY ? n
							- p.touchStartY
							: 0, s = p.x + m, q = p.y + l;
					r.stopPropagation();
					p.touchStartX = o;
					p.touchStartY = n;
					if (s >= 0 || s < p.maxScrollX) {
						s = p.options.bounce ? Math.round(p.x + m / 3)
								: (s >= 0 || p.maxScrollX >= 0) ? 0
										: p.maxScrollX
					}
					if (q >= 0 || q < p.maxScrollY) {
						q = p.options.bounce ? Math.round(p.y + l / 3)
								: (q >= 0 || p.maxScrollY >= 0) ? 0
										: p.maxScrollY
					}
					if (p.distX + p.distY > 5) {
						if (p.distX - 3 > p.distY) {
							q = p.y;
							l = 0
						} else {
							if (p.distY - 3 > p.distX) {
								s = p.x;
								m = 0
							}
						}
						p.setPosition(s, q);
						p.moved = true;
						p.directionX = m > 0 ? -1 : 1;
						p.directionY = l > 0 ? -1 : 1
					} else {
						p.distX += Math.abs(m);
						p.distY += Math.abs(l)
					}
				},
				touchEnd : function(t) {
					if (!this.scrolling) {
						return

					}
					var s = this, o = t.timeStamp - s.scrollStartTime, w = a ? t.changedTouches[0]
							: t, u, v, n, l, m = 0, r = s.x, q = s.y, p;
					s.scrolling = false;
					if (!s.moved) {
						s.resetPosition();
						if (a) {
							u = w.target;
							while (u.nodeType != 1) {
								u = u.parentNode
							}
							v = $doc.createEvent("MouseEvents");
							v.initMouseEvent("click", true, true, t.view, 1,
									w.screenX, w.screenY, w.clientX, w.clientY,
									t.ctrlKey, t.altKey, t.shiftKey, t.metaKey,
									0, null);
							v._fake = true;
							u.dispatchEvent(v)
						}
						return

					}
					if (!s.options.snap && o > 250) {
						s.resetPosition();
						return

					}
					if (s.options.momentum) {
						n = s.scrollX === true ? s
								.momentum(s.x - s.scrollStartX, o,
										s.options.bounce ? -s.x + s.scrollWidth
												/ 5 : -s.x,
										s.options.bounce ? s.x
												+ s.scrollerWidth
												- s.scrollWidth + s.scrollWidth
												/ 5 : s.x + s.scrollerWidth
												- s.scrollWidth) : {
							dist : 0,
							time : 0
						};
						l = s.scrollY === true ? s
								.momentum(
										s.y - s.scrollStartY,
										o,
										s.options.bounce ? -s.y
												+ s.scrollHeight / 5 : -s.y,
										s.options.bounce ? (s.maxScrollY < 0 ? s.y
												+ s.scrollerHeight
												- s.scrollHeight
												: 0)
												+ s.scrollHeight / 5
												: s.y + s.scrollerHeight
														- s.scrollHeight)
								: {
									dist : 0,
									time : 0
								};
						m = Math.max(Math.max(n.time, l.time), 1);
						r = s.x + n.dist;
						q = s.y + l.dist
					}
					if (s.options.snap) {
						p = s.snap(r, q);
						r = p.x;
						q = p.y;
						m = Math.max(p.time, m)
					}
					s.scrollTo(r, q, m + "ms")
				},
				transitionEnd : function() {
					var l = this;
					$doc.removeEventListener("webkitTransitionEnd", l, false);
					l.resetPosition()
				},
				resetPosition : function() {
					var l = this, n = l.x, m = l.y;
					if (l.x >= 0) {
						n = 0
					} else {
						if (l.x < l.maxScrollX) {
							n = l.maxScrollX
						}
					}
					if (l.y >= 0 || l.maxScrollY > 0) {
						m = 0
					} else {
						if (l.y < l.maxScrollY) {
							m = l.maxScrollY
						}
					}
					if (n != l.x || m != l.y) {
						l.scrollTo(n, m)
					} else {
						if (l.moved) {
							l.onScrollEnd();
							l.moved = false
						}
						if (l.scrollBarX) {
							l.scrollBarX.hide()
						}
						if (l.scrollBarY) {
							l.scrollBarY.hide()
						}
					}
				},
				snap : function(l, o) {
					var m = this, n;
					if (m.directionX > 0) {
						l = Math.floor(l / m.scrollWidth)
					} else {
						if (m.directionX < 0) {
							l = Math.ceil(l / m.scrollWidth)
						} else {
							l = Math.round(l / m.scrollWidth)
						}
					}
					m.pageX = -l;
					l = l * m.scrollWidth;
					if (l > 0) {
						l = m.pageX = 0
					} else {
						if (l < m.maxScrollX) {
							m.pageX = m.maxPageX;
							l = m.maxScrollX
						}
					}
					if (m.directionY > 0) {
						o = Math.floor(o / m.scrollHeight)
					} else {
						if (m.directionY < 0) {
							o = Math.ceil(o / m.scrollHeight)
						} else {
							o = Math.round(o / m.scrollHeight)
						}
					}
					m.pageY = -o;
					o = o * m.scrollHeight;
					if (o > 0) {
						o = m.pageY = 0
					} else {
						if (o < m.maxScrollY) {
							m.pageY = m.maxPageY;
							o = m.maxScrollY
						}
					}
					n = Math.round(Math.max(Math.abs(m.x - l) / m.scrollWidth
							* 500, Math.abs(m.y - o) / m.scrollHeight * 500));
					return {
						x : l,
						y : o,
						time : n
					}
				},
				scrollTo : function(m, l, o) {
					var n = this;
					if (n.x == m && n.y == l) {
						n.resetPosition();
						return

					}
					n.moved = true;
					n.setTransitionTime(o || "350ms");
					n.setPosition(m, l);
					if (o === "0" || o == "0s" || o == "0ms") {
						n.resetPosition()
					} else {
						$doc.addEventListener("webkitTransitionEnd", n, false)
					}
				},
				scrollToPage : function(n, m, p) {
					var o = this, l;
					if (!o.options.snap) {
						o.pageX = -Math.round(o.x / o.scrollWidth);
						o.pageY = -Math.round(o.y / o.scrollHeight)
					}
					if (n == "next") {
						n = ++o.pageX
					} else {
						if (n == "prev") {
							n = --o.pageX
						}
					}
					if (m == "next") {
						m = ++o.pageY
					} else {
						if (m == "prev") {
							m = --o.pageY
						}
					}
					n = -n * o.scrollWidth;
					m = -m * o.scrollHeight;
					l = o.snap(n, m);
					n = l.x;
					m = l.y;
					o.scrollTo(n, m, p || "500ms")
				},
				scrollToElement : function(m, o) {
					m = typeof m == "object" ? m : this.element
							.querySelector(m);
					if (!m) {
						return

					}
					var n = this, l = n.scrollX ? -m.offsetLeft : 0, p = n.scrollY ? -m.offsetTop
							: 0;
					if (l >= 0) {
						l = 0
					} else {
						if (l < n.maxScrollX) {
							l = n.maxScrollX
						}
					}
					if (p >= 0) {
						p = 0
					} else {
						if (p < n.maxScrollY) {
							p = n.maxScrollY
						}
					}
					n.scrollTo(l, p, o)
				},
				momentum : function(s, m, q, l) {
					var p = 2.5, r = 1.2, n = Math.abs(s) / m * 1000, o = n * n
							/ p / 1000, t = 0;
					if (s > 0 && o > q) {
						n = n * q / o / p;
						o = q
					} else {
						if (s < 0 && o > l) {
							n = n * l / o / p;
							o = l
						}
					}
					o = o * (s < 0 ? -1 : 1);
					t = n / r;
					return {
						dist : Math.round(o),
						time : Math.round(t)
					}
				},
				destroy : function(l) {
					var m = this;
					$wnd
							.removeEventListener(
									"onorientationchange" in window ? "orientationchange"
											: "resize", m, false);
					m.element.removeEventListener(f, m, false);
					m.element.removeEventListener(i, m, false);
					m.element.removeEventListener(e, m, false);
					$doc.removeEventListener("webkitTransitionEnd", m, false);
					if (m.options.checkDOMChanges) {
						m.element.removeEventListener("DOMSubtreeModified", m,
								false)
					}
					if (m.scrollBarX) {
						m.scrollBarX = m.scrollBarX.remove()
					}
					if (m.scrollBarY) {
						m.scrollBarY = m.scrollBarY.remove()
					}
					if (l) {
						m.wrapper.parentNode.removeChild(m.wrapper)
					}
					return null
				}
			};
			function k(m, r, q, n, l) {
				var o = this, p = document;
				o.dir = m;
				o.fade = q;
				o.shrink = n;
				o.uid = ++c;
				o.bar = p.createElement("div");
				o.bar.style.cssText = "position:absolute;top:0;left:0;-webkit-transition-timing-function:cubic-bezier(0,0,0.25,1);pointer-events:none;-webkit-transition-duration:0;-webkit-transition-delay:0;-webkit-transition-property:-webkit-transform;z-index:10;background:"
						+ l
						+ ";-webkit-transform:"
						+ h
						+ "0,0"
						+ b
						+ ";"
						+ (m == "horizontal" ? "-webkit-border-radius:3px 2px;min-width:6px;min-height:5px"
								: "-webkit-border-radius:2px 3px;min-width:5px;min-height:6px");
				o.wrapper = p.createElement("div");
				o.wrapper.style.cssText = "-webkit-mask:-webkit-canvas(scrollbar"
						+ o.uid
						+ o.dir
						+ ");position:absolute;z-index:10;pointer-events:none;overflow:hidden;opacity:0;-webkit-transition-duration:"
						+ (q ? "300ms" : "0")
						+ ";-webkit-transition-delay:0;-webkit-transition-property:opacity;"
						+ (o.dir == "horizontal" ? "bottom:2px;left:2px;right:7px;height:5px"
								: "top:2px;right:2px;bottom:7px;width:5px;");
				o.wrapper.appendChild(o.bar);
				r.appendChild(o.wrapper)
			}
			k.prototype = {
				init : function(l, n) {
					var o = this, q = document, p = Math.PI, m;
					if (o.dir == "horizontal") {
						if (o.maxSize != o.wrapper.offsetWidth) {
							o.maxSize = o.wrapper.offsetWidth;
							m = q.getCSSCanvasContext("2d", "scrollbar" + o.uid
									+ o.dir, o.maxSize, 5);
							m.fillStyle = "rgb(0,0,0)";
							m.beginPath();
							m.arc(2.5, 2.5, 2.5, p / 2, -p / 2, false);
							m.lineTo(o.maxSize - 2.5, 0);
							m.arc(o.maxSize - 2.5, 2.5, 2.5, -p / 2, p / 2,
									false);
							m.closePath();
							m.fill()
						}
					} else {
						if (o.maxSize != o.wrapper.offsetHeight) {
							o.maxSize = o.wrapper.offsetHeight;
							m = q.getCSSCanvasContext("2d", "scrollbar" + o.uid
									+ o.dir, 5, o.maxSize);
							m.fillStyle = "rgb(0,0,0)";
							m.beginPath();
							m.arc(2.5, 2.5, 2.5, p, 0, false);
							m.lineTo(5, o.maxSize - 2.5);
							m.arc(2.5, o.maxSize - 2.5, 2.5, 0, p, false);
							m.closePath();
							m.fill()
						}
					}
					o.size = Math.max(Math.round(o.maxSize * o.maxSize / n), 6);
					o.maxScroll = o.maxSize - o.size;
					o.toWrapperProp = o.maxScroll / (l - n);
					o.bar.style[o.dir == "horizontal" ? "width" : "height"] = o.size
							+ "px"
				},
				setPosition : function(m) {
					var l = this;
					if (l.wrapper.style.opacity != "1") {
						l.show()
					}
					m = Math.round(l.toWrapperProp * m);
					if (m < 0) {
						m = l.shrink ? m + m * 3 : 0;
						if (l.size + m < 7) {
							m = -l.size + 6
						}
					} else {
						if (m > l.maxScroll) {
							m = l.shrink ? m + (m - l.maxScroll) * 3
									: l.maxScroll;
							if (l.size + l.maxScroll - m < 7) {
								m = l.size + l.maxScroll - 6
							}
						}
					}
					m = l.dir == "horizontal" ? h + m + "px,0" + b : h + "0,"
							+ m + "px" + b;
					l.bar.style.webkitTransform = m
				},
				show : function() {
					if (d) {
						this.wrapper.style.webkitTransitionDelay = "0"
					}
					this.wrapper.style.opacity = "1"
				},
				hide : function() {
					if (d) {
						this.wrapper.style.webkitTransitionDelay = "350ms"
					}
					this.wrapper.style.opacity = "0"
				},
				remove : function() {
					this.wrapper.parentNode.removeChild(this.wrapper);
					return null
				}
			};
			var d = ("WebKitCSSMatrix" in window && "m11" in new WebKitCSSMatrix()), g = (/iphone|ipad/gi)
					.test(navigator.appVersion), a = ("ontouchstart" in window), f = a ? "touchstart"
					: "mousedown", i = a ? "touchmove" : "mousemove", e = a ? "touchend"
					: "mouseup", h = "translate" + (d ? "3d(" : "("), b = d ? ",0)"
					: ")", c = 0;
			$wnd.iScroll = j
		})();
	}-*/;

}