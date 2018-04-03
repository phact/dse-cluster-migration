import {ACTION_TYPES} from "../actions/actions";
export const appReducer = (oldState, action) => {
    const state = {...oldState};

    switch (action.type) {
        case ACTION_TYPES.initMigration: {
            return {
                migrationDef: {
                    fromHost: "localhost",
                    toHost: "localhost",
                    fromUser: "cassandra",
                    toUser: "cassandra",
                    fromPw: "cassandra",
                    toPw: "cassandra",
                    fromTable: "test",
                    toTable: "test2",
                    fromKs: "test",
                    toKs: "test"
                }
            }
        }
        case ACTION_TYPES.changeMigrationField: {
            return{
            ...state,
                migrationDef: {
                    ...state.migrationDef,
                    [action.key]: action.value
                }
            }
        }
        default: {
            break;
        }
    }
    return state;
};
