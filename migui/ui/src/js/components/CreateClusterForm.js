import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import TextField from 'material-ui/TextField';
import Grid from 'material-ui/Grid';
import Button from 'material-ui/Button';


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
          value="localhost"
          className={classes.textField}
          margin="normal"
        />
        <TextField
          id="toHost"
          label="To Host"
          value="localhost"
          className={classes.textField}
          margin="normal"
        />
        <br/>
        </Grid>
        <Grid>
        <TextField
          id="fromUser"
          label="From User"
          value="cassandra"
          className={classes.textField}
          margin="normal"
        />
        <TextField
          id="toUser"
          label="To User"
          value="cassandra"
          className={classes.textField}
          margin="normal"
        />
        <br/>
        </Grid>
        <Grid>

        <TextField
          id="fromPw"
          label="From Password"
          value="cassandra"
          className={classes.textField}
          margin="normal"
        />

        <TextField
          id="toPw"
          label="To Password"
          value="cassandra"
          className={classes.textField}
          margin="normal"
        />
        <br/>
        </Grid>
        <Grid>
        <TextField
          id="fromTable"
          label="From Table"
          value="test"
          className={classes.textField}
          margin="normal"
        />

        <TextField
          id="toTable"
          label="To Table"
          value="test2"
          className={classes.textField}
          margin="normal"
        />
        <br/>
        </Grid>

        <Grid>
        <TextField
          id="fromKs"
          label="From Keyspace"
          value="test"
          className={classes.textField}
          margin="normal"
        />

        <TextField
          id="toKs"
          label="To Keyspace"
          value="test"
          className={classes.textField}
          margin="normal"
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

CreateClusterForm.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(CreateClusterForm);
