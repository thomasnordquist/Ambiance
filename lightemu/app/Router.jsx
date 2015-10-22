var React = require("react");
var Router = require("react-router");
var Route = Router.Route;
var DefaultRoute = Router.DefaultRoute;

var App = require("./App");
var Kicker = require('./Kicker')

// polyfill
if(!Object.assign)
    Object.assign = React.__spread;

var routes = (
    <Route name="app" handler={App} path="/">
        <DefaultRoute handler={Kicker} />
        <Route name="kicker" handler={Kicker} />
    </Route>
);

/*
<DefaultRoute handler={Persons} />
<Route name="people" handler={Persons} />
<Route name="devices" handler={Devices} />*/
// Or, if you'd like to use the HTML5 history API for cleaner URLs:

Router.run(routes, Router.HistoryLocation, function (Handler) {
    React.render(<Handler />, document.getElementById('content'));
});
