import React from 'react';
import Header from './Header';
import Footer from './Footer';
import CreateClusterFormContainer from './CreateClusterForm';
import {MuiThemeProvider,  createMuiTheme } from 'material-ui/styles';

const theme = createMuiTheme({
  palette: {
    primary: {
      light: '#4fa9c8',
      main: '#007a97',
      dark: '#004e69',
      contrastText: '#fff'
    },
    secondary: {
      light: '#ff8e44',
      main: '#ca5f14',
      dark: '#933100',
      contrastText: '#fff'
    }
  }
});

class App extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
                <MuiThemeProvider theme={theme}>
                <div>
                    <Header/>
                    <CreateClusterFormContainer/>
                    <Footer/>
                </div>
                </MuiThemeProvider>
        );
    }
}

App.displayName = 'App;';

export default App;
