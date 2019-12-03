/* global QUnit */
QUnit.config.autostart = false;

sap.ui.getCore().attachInit(function () {
	"use strict";

	sap.ui.require([
		"sap/gantt/sample/BasicGanttChart/test/unit/AllTests"
	], function () {
		QUnit.start();
	});
});