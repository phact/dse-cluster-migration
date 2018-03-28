import {ACTION_TYPES} from "../actions/actions";
export const appReducer = (oldState, action) => {
    const state = {...oldState};

    switch (action.type) {
        case ACTION_TYPES.triggerVertexMenu: {
            state.menuContext = action.event;
            break;
        }
        case ACTION_TYPES.hideVertexMenu: {
            delete state.menuContext;
            break;
        }
        case ACTION_TYPES.showFlashMsg: {
            state.flashMsg = action.msg;
            break;
        }
        case ACTION_TYPES.hideFlashMsg : {
            delete state.flashMsg;
            break;
        }
        default: {
            break;
        }
    }
    return state;
};
