import React from 'react';

const Header = () => {
    return (
        <div
            style={{
                // borderBottom: '2px solid #20A7CA',
                borderBottom: '2px solid #3d3d3d',
                align: 'center',
                // height: '43px'
                height: '65px',
                background: '#1d1d1d'
            }}>
            <img
                // src={require('../../img/x-logo.svg')}
                src={require('../../img/custom/tdlogo.png')}
                style={{
                    // height: '40px',
                    height: '60px',
                    verticalAlign: 'top'
                }}
                tabIndex={0}
            />

            <div
                style={{
                    color: '$medium-grey',
                    display: 'inline-block'
                }}
            >
                {/*DataStax<br/> Migration UI*/}
            </div>
        </div>
    );
};

Header.displayName = 'Header';
export default Header;
