import {get} from '../utils/Requests'
import {notify} from './NavigationActions'

export const ACTION_TYPES = {
    initMigration: 'INIT_MIGRATION',
    changeMigrationField: 'CHANGE_MIGRATION_FIELD',
    changeScreen: 'CHANGE_SCREEN',
    drawerToggle: 'DRAWER_TOGGLE',
    closeSnackbar: 'CLOSE_SNACKBAR',
    notify: 'NOTIFY',
};

export function initMigration(){
    return {
        type: ACTION_TYPES.initMigration
    }
}

export function changeMigrationField(name, value){
    return {
        type: ACTION_TYPES.changeMigrationField,
        key: name,
        value: value
    }
}

export function migrate(migrationDef){
    if (migrationDef){
        return (dispatch, getState) => {
            get({
                url: '/api/v0/migui/submitSparkJob',
                params: migrationDef,
                success: function(response){
                    dispatch(notify("Migration Submitted "+response))
                },
                dispatch: dispatch
            })
        }
    }else{
        return {
            type: false
        }
    }
}
