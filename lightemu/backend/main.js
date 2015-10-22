'use strict';

var dgram = require('dgram');
var config = require('../config');
var UI = require('./Ui')({port: config.applicationPort, serverMode: 'dev'});
var _ = require('lodash');
var UIEvents = require('../Events/UIEvents');

var io = require('socket.io-client');
var events = io.connect(config.discoveryUrl);

events.on('asd', function(host) {
	//handleHost(host);
});

var udp = dgram.createSocket('udp4');
udp.bind(1234, function() {
  //s.addMembership('224.0.0.114');
});

class Color {
	constructor(buffer) {
		this.red = buffer[0].toString(16)
		this.green = buffer[1].toString(16)
		this.blue = buffer[2].toString(16)

		this.red.length == 1 ? this.red = "0"+this.red : null;
		this.green.length == 1 ? this.green = "0"+this.green : null;
		this.blue.length == 1 ? this.blue = "0"+this.blue : null;
	}
}

udp.on('message', (buf, rinfo) => {
		let bufferFrames = []
		for(let pos=0; pos < buf.length; pos+=4) {
			bufferFrames[pos/4] = buf.slice(pos, pos+4);
		}
		bufferFrames = bufferFrames.slice(4);
		let colors = bufferFrames.map( bFrame => new Color(bFrame) )
		//colors.forEach((c, k) => console.log(k + ": #"+c.red+c.green+c.blue))
		UI.emit(UIEvents.colors, UI.all(), colors);
})

function getPersons(target) {
	UI.emit(UIEvents.persons, target, 'result');


};

//UI.on(UIEvents.setOwnerOfDevice, setOwnerOfDevice);
