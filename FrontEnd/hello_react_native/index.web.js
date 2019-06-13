import { AppRegistry } from 'react-native';
import App from './src/App';

AppRegistry.registerComponent('hello_react_native2', () => App);
AppRegistry.runApplication('hello_react_native2', {
    rootTag: document.getElementById('react-root')
});