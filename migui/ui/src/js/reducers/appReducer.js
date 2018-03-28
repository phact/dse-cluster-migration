import {ACTION_TYPES} from "../actions/actions";
export const appReducer = (oldState, action) => {
    const state = {...oldState};

    switch (action.type) {
        case ACTION_TYPES.migrate: {
            state.migratedState = action.migratedState;
            break;
        }
        default: {
            break;
        }
    }
    return state;
};
