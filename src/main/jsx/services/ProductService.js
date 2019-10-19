import * as axios from 'axios';

export const getProductList = () => {
    return new Promise((resolve, reject) => {
        axios.post("/product/categories")
            .then((result) => {
                const data = result.data;
                resolve(data);
                return;
            })
            .catch((err) => {
                console.log(err);
                reject(err.message);
                return;
            })
    })
}

export const getMyProductList = () => {
    return new Promise((resolve, reject) => {
        axios.get("/member/mypage/products")
            .then((result) => {
                const data = result.data;
                resolve(data);
                return;
            })
            .catch((err) => {
                console.log(err);
                reject(err.message);
                return;
            })
    })
}

export const getMyRequestProductList = () => {
    return new Promise((resolve, reject) => {
        axios.get("/member/mypage/requestProducts")
            .then((result) => {
                const data = result.data;
                resolve(data);
                return;
            })
            .catch((err) => {
                console.log(err);
                reject(err.message);
                return;
            })
    })
}

export const deleteProduct= (pageNo) => {
    return new Promise((resolve, reject) => {
        axios.get("/product/delete/"+pageNo)
            .then((result) => {
                const data = result.data;
                resolve(data);
                return;
            })
            .catch((err) => {
                console.log(err);
                reject(err.message);
                return;
            })
    })
}