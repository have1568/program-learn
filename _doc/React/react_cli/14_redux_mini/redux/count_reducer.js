
export default (preState = 0, action) => {
    console.log(preState,action)
    const {type, data} = action;
    switch (type) {
        case "increment":
            console.log("increment")
            return preState + data;
        case "decrement":
            return preState - data;
        default:
            return preState;
    }
}