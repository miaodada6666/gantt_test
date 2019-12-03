/* global QUnit */
QUnit.config.autostart = false;

sap.ui.getCore().attachInit(function () {
	"use strict";

	sap.ui.require([
		"sap/gantt/sample/BasicGanttChart/test/integration/AllJourneys"
	], function () {
		QUnit.start();
	});
});