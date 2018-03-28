export const ACTION_TYPES = {
    migrate: 'MIGRATE'
};

const migrate = (msg) => {
    console.log('call server');
    return {
        type: ACTION_TYPES.migrate,
        migratedState: msg
    };
};

export const Actions = {
    migrate
};
