import {ACTION_TYPES} from "../actions/actions";
import {get} from '../utils/Requests';

export const changeScreen = (page) => {
    window.sessionStorage.setItem("page", page)
    return {
        type: ACTION_TYPES.changeScreen,
        page: page
    }
}

export const drawerToggle = (drawerOpen) => {
    return {
        type: ACTION_TYPES.drawerToggle,
        drawerOpen: drawerOpen
    }
}

export const handleSnackBarClose = () => {
    return {
        type: ACTION_TYPES.closeSnackbar,
    }
}

export const notify = (msg, type) => {
    return {
        type: ACTION_TYPES.notify,
        indicatormsg: msg,
        indicatorMsgShown: true,
        indicatorType: type
    }
}
