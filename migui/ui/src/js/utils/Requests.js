import axios from 'axios';

export function get({url, params} = {}) {
    axios.get(url, {
        headers: {
            "accept": "application/json",
            "cache-control": "no-cache"
        },
        params: params,
    })
    .then(function(response){
        console.log(response)
    })
    .catch(function(error) {
        console.log(error.response)
    })
}

export function post({url, params} = {}) {
    axios.post(url, params, {
        headers: {
            "accept": "application/json",
            "content-type": "application/json",
            "cache-control": "no-cache"
        },
    })
    .then(function(response){
        console.log(response)
    })
    .catch(function (error) {
        console.log(error.response)
    })
}

export function remove({url} = {}) {
    axios.delete(url, {
        headers: {
            "accept": "application/json",
            "content-type": "application/json",
            "cache-control": "no-cache"
        },
    })
    .then(function(response){
        console.log(response)
    })
    .catch(function (error) {
        console.log(error.response.headers)
    })
}
