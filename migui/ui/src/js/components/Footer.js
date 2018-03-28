import React from 'react';

const Footer = () => {
    return (
        <div style={{
            fontSize: 'x-small',
            textAlign: 'center',
            width: '100%',
            position: 'absolute',
            bottom: '0px',
            borderTop: '2px solid #3d3d3d',
            background: '#1d1d1d'
        }}>
            <p style={{
                color: 'grey'
            }}>
                © DataStax Inc 2018
                Prepared by DataStax Inc 2018
            </p>
        </div>
    );
};

Footer.displayName = 'Footer';

export default Footer;
