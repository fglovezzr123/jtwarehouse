Strophe.addNamespace('RSM', 'http://jabber.org/protocol/rsm');
Strophe.RSM = function(options) {
	this.attribs = ['max', 'first', 'last', 'after', 'before', 'index', 'count'];
	if (typeof options.xml != 'undefined') {
		this.fromXMLElement(options.xml);
	} else {
		for (var ii = 0; ii < this.attribs.length; ii++) {
			var attrib = this.attribs[ii];
			this[attrib] = options[attrib];
		}
	}
};
Strophe.RSM.prototype = {
	toXML: function() {
		var xml = $build('set', {
			xmlns: Strophe.NS.RSM
		});
		for (var ii = 0; ii < this.attribs.length; ii++) {
			var attrib = this.attribs[ii];
			if (typeof this[attrib] != 'undefined') {
				xml = xml.c(attrib).t(this[attrib].toString()).up();
			}
		}
		return xml.tree();
	},

	next: function(max) {
		var newSet = new Strophe.RSM({
			max: max,
			after: this.last
		});
		return newSet;
	},

	previous: function(max) {
		var newSet = new Strophe.RSM({
			max: max,
			before: this.first
		});
		return newSet;
	},

	fromXMLElement: function(xmlElement) {
		for (var ii = 0; ii < this.attribs.length; ii++) {
			var attrib = this.attribs[ii];
			var elem = xmlElement.getElementsByTagName(attrib)[0];
			if (typeof elem != 'undefined' && elem !== null) {
				this[attrib] = Strophe.getText(elem);
				if (attrib == 'first') {
					this.index = elem.getAttribute('index');
				}
			}
		}
	}
};
Strophe.addConnectionPlugin('mam', {
	_c: null,
	_p: ["with", "start", "end"],
	init: function(conn) {
		this._c = conn;
		Strophe.addNamespace('MAM', 'urn:xmpp:mam:tmp');
	},
	query: function(jid, options) {
		var _p = this._p;
		var attr = {
			type: "get",
			to: jid
		};
		var mamAttr = {
			xmlns: Strophe.NS.MAM
		};
		var iq = $iq(attr).c("query", mamAttr);
		for (i = 0; i < this._p.length; i++) {
			var pn = _p[i];
			var p = options[pn];
			delete options[pn];
			if (!!p) {
				iq.c(pn).t(p).up();
			}
		}
		var onMessage = options["onMessage"];
		delete options['onMessage'];
		var onComplete = options["onComplete"];
		delete options['onComplete'];
		iq.cnode(new Strophe.RSM(options).toXML());
		this._c.addHandler(onMessage, Strophe.NS.MAM, "message", null);
		return this._c.sendIQ(iq, onComplete);
	},
});