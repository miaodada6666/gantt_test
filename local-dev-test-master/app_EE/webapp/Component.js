sap.ui.define(["sap/suite/ui/generic/template/lib/AppComponent"],
function (AppComponent) {
	return AppComponent.extend("app_EE.Component", {
		
			metadata : {
			rootView : "com.sap.gantt.gantt.Component",
			dependencies : {
				libs : [
					"sap.gantt",
					"sap.ui.table",
					"sap.m"
				]
			},
			config : {
				sample : {
					files : [
						"gantt.view.xml",
						"gantt.controller.js",
						"data.json"
					]
				}
			}
		}
		// metadata: {
		// 	"manifest": "json"
		// }
	});
});