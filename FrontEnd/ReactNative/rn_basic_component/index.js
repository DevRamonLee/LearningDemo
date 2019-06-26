/**
 * @format
 */

import {AppRegistry} from 'react-native';
import App from './App';
import Meituan from './Meituan';
import QqLogin from './QqLogin';
import {name as appName} from './app.json';

AppRegistry.registerComponent(appName, () => QqLogin);
