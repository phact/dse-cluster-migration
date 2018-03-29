import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import TextField from 'material-ui/TextField';
import Grid from 'material-ui/Grid';
import Button from 'material-ui/Button';
import {initMigration, changeMigrationField, migrate} from "../actions/Actions";


const styles = theme => ({
  container: {
    display: 'flex',
    flexWrap: 'wrap'
  },
  textField: {
    marginLeft: theme.spacing.unit,
    marginRight: theme.spacing.unit,
    width: 200
  },
  menu: {
    width: 200
  },
  grid: {
    width: '100%'
  },
  form: {
    marginLeft: theme.spacing.unit,
    marginRight: theme.spacing.unit,
  }
});

class CreateClusterForm extends React.Component {
    componentWillMount() {
        this.props.init();
    }
    render() {
        const { classes } = this.props;

        return (
                <form className={classes.container} noValidate autoComplete="off">
                <div style={{padding:25}}>
                <div>
                <h2>Cluster Migration Settings</h2>
                <br/>
                <p>Populate this screen and click migrate to move your data to DSE using DSE Analytics.</p>
                </div>
                <div>
                <Grid>
                <TextField
                id="fromHost"
                label="From Host"
                value={this.props.migrationDef ? this.props.migrationDef.fromHost : ""}
                className={classes.textField}
                margin="normal"
                onChange={(e) => {
                    console.log(e.target.value)
            this.props.changeMigrationField("fromHost", e.target.value);
                }}
                />
                    <TextField
                    id="toHost"
                    label="To Host"
                    value={this.props.migrationDef ? this.props.migrationDef.toHost : ""}
                    className={classes.textField}
                    margin="normal"
                    onChange={(e, v) => {
                        this.props.changeMigrationField("toHost", v)
                    }}
                />
                    <br/>
                    </Grid>
                    <Grid>
                    <TextField
                    id="fromUser"
                    label="From User"
                    value={this.props.migrationDef ? this.props.migrationDef.fromUser : ""}
                    className={classes.textField}
                margin="normal"
                    onChange={(e, v) => {
                        this.props.changeMigrationField("fromUser", v)
                    }}
                />
                    <TextField
                    id="toUser"
                    label="To User"
                    value={this.props.migrationDef ? this.props.migrationDef.toUser : ""}
                    className={classes.textField}
                margin="normal"
                    onChange={(e, v) => {
                        this.props.changeMigrationField("toUser", v)
                    }}
                />
                    <br/>
                    </Grid>
                    <Grid>

                    <TextField
                    id="fromPw"
                    label="From Password"
                    value={this.props.migrationDef ? this.props.migrationDef.fromPw : ""}
                    className={classes.textField}
                margin="normal"
                    onChange={(e, v) => {
                        this.props.changeMigrationField("fromPw", v)
                    }}
                />

                    <TextField
                    id="toPw"
                    label="To Password"
                    value={this.props.migrationDef ? this.props.migrationDef.toPw : ""}
                    className={classes.textField}
                margin="normal"
                    onChange={(e, v) => {
                        this.props.changeMigrationField("toPw", v)
                    }}
                />
                    <br/>
                    </Grid>
                    <Grid>
                    <TextField
                    id="fromTable"
                    label="From Table"
                    value={this.props.migrationDef ? this.props.migrationDef.fromTable : ""}
                    className={classes.textField}
                margin="normal"
                    onChange={(e, v) => {
                        this.props.changeMigrationField("fromTable", v)
                    }}
                />

                    <TextField
                    id="toTable"
                    label="To Table"
                    value={this.props.migrationDef ? this.props.migrationDef.toTable : ""}
                    className={classes.textField}
                margin="normal"
                    onChange={(e, v) => {
                        this.props.changeMigrationField("toTable", v)
                    }}
                />
                    <br/>
                    </Grid>

                    <Grid>
                    <TextField
                    id="fromKs"
                    label="From Keyspace"
                    value={this.props.migrationDef ? this.props.migrationDef.fromKs : ""}
                    className={classes.textField}
                margin="normal"
                    onChange={(e, v) => {
                        this.props.changeMigrationField("fromKs", v)
                    }}
                />

                    <TextField
                    id="toKs"
                    label="To Keyspace"
                    value={this.props.migrationDef ? this.props.migrationDef.toKs : ""}
                    className={classes.textField}
                margin="normal"
                    onChange={(e, v) => {
                        this.props.changeMigrationField("toKs", v)
                    }}
                />
                    <br/>
                    </Grid>
                    </div>
                    <div>
                    <Button variant="raised" color="primary" className={classes.button}>
                    Migrate
                    </Button>
                    </div>
                    </div>
                    </form>
                    );
    }
}

const mapStateToProps = (state, ownProps) => {
    if (state.app !== null){
        return {
            migrationDef: state.app.migrationDef,
        }
    }
    return
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        init: () => {
                  dispatch(initMigration())
              },
        changeMigrationField: (key, value) => {
                                  dispatch(changeMigrationField(key, value))
                              },
        migrate: (migrationDef) => {
                     dispatch(migrate(migrationDef))
                 }
    }
}

CreateClusterForm.propTypes = {
    classes: PropTypes.object.isRequired,
};

const CreateClusterFormContainer = connect(
        mapStateToProps,
        mapDispatchToProps
        )(CreateClusterForm)

    export default withStyles(styles)(CreateClusterFormContainer);
