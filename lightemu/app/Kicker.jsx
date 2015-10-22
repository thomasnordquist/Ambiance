var React = require("react");
var UIEvents = require('../Events/UIEvents');

var Kicker = React.createClass({
    getInitialState:function() {
        var colors = new Array(60);
        return {colors: colors.map(function(){return{red: 0, green: 0, blue: 0}})}
    },
    updateColors: function(colors) {
      this.setState({colors: colors})
    },
    componentDidMount: function() {
        this.props.events.on(UIEvents.colors, this.updateColors);
    },
    componentWillUnmount: function() {
        this.props.events.removeListener(UIEvents.colors, this.updateColors);
    },
    renderColors(colors) {
        return colors.map(
            (color, key) => <div key={key} className="rect" style={{backgroundColor: '#'+color.red+color.green+color.blue}}></div>
        )
    },
    render: function() {
      console.log('colors', this.state.colors.length);
        return (
            <div>
              <div className="row">
                <div className="ledColumn">
                  {this.renderColors(this.state.colors.splice(0,15))}
                </div>
                <div className="ledColumn">
                  {this.renderColors(this.state.colors.splice(0,15).reverse())}
                </div>
              </div>

              <div className="kicker">

              </div>

              <div className="row">
                <div className="ledColumn">
                  {this.renderColors(this.state.colors.splice(0,15).reverse())}
                </div>
                <div className="ledColumn">
                  {this.renderColors(this.state.colors.splice(0,15))}
                </div>
              </div>
            </div>
        );
    }
});
module.exports = Kicker;
