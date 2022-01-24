# 1、jsx语法规则

* 1.定义虚拟DOM时，不要写引号。
* 2.标签中混入JS表达式时要用{}。
* 3.样式的类名指定不要用class，要用className。
* 4.内联样式，要用style={{key:value}}的形式去写。
* 5.只有一个根标签
* 6.标签必须闭合
* 7.标签首字母
    * (1).若小写字母开头，则将该标签转为html中同名元素，若html中无该标签对应的同名元素，则报错。
    * (2).若大写字母开头，react就去渲染对应的组件，若组件没有定义，则报错。
* 示例

```jsx

const myId = 'ID'
const myData = 'HeLlo'

//1.创建虚拟DOM
const VDOM = (
    <div>
        <h2 className="title" id={myId.toLowerCase()}>
            <span style={{color: 'white', fontSize: '29px'}}>{myData.toLowerCase()}</span>
        </h2>
        <h2 className="title" id={myId.toUpperCase()}>
            <span style={{color: 'white', fontSize: '29px'}}>{myData.toLowerCase()}</span>
        </h2>
        <input type="text"/>
    </div>
)

```

# 2、 组件的创建和相关概念

### 2.1 分为函数式组件和类式组件
### 2.2 有状态的组件是复杂组件，没有state的是简单组件

```jsx

//创建函数式组件
// function App() {
//     return <h1>简单组件！</h1>
// }
//ES6方式创建
let App = () => {
    //undefined  严格模式导致
    console.log(this)
    return <h1>简单组件！</h1>
}

//Class方式创建
class AppClass extends React.Component {
    //必须有render函数
    render() {
        //this 指向 AppClass实例
        console.log(this)
        return <h1>AppClass</h1>
    }
}

//这种上市渲染相当于虚拟DOM,dev-tools里不显示
//ReactDOM.render(app(),document.getElementById("app"))

//大写自闭和标签
//ReactDOM.render(<App/> ,document.getElementById("app"))

//<AppClass/> 会触发 new AppClass(),并通过改实例调用 render()函数
ReactDOM.render(<AppClass/>, document.getElementById("app"))

```

### 2.3 绑定事件和修改状态示例

```jsx

//Class方式创建
class AppClass extends React.Component {

    constructor(props) {
        super(props);
        //初始化state对象
        this.state = {isShow: false}
        //给当前实例绑定一个clickTest，并将方法的this指定为 this ,解决this为 undefined 的问题
        this.clickTest = this.clickTest.bind(this)
    }

    clickTest() {
        console.log("click!!!")
        //this 为 undefined
        //只能是AppClass实例对象调用的才能获得this,类中的方法this指向该实例
        console.log(this)

        //必须先读取出来，不能直接在setState里取反
        let current = this.state.isShow;
        //这里的设置并没有覆盖整个state对象，只是isShow属性变了，合并机制
        this.setState({isShow: !current})
    }

    //必须有render函数
    render() {
        //this 指向 AppClass实例
        console.log(this)

        //用表达式返回结果，不能直接 this.
        //绑定事件调用方法，必须加 this. 这里没有调用clickTest 导致点击事件获取不到 this
        //读取状态,必须读取出来，否则不生效
        let showText = this.state.isShow;
        return <h1 onClick={this.clickTest}>测试state:{showText ? '显示' : '隐藏'}</h1>
    }
}

//<AppClass/> 会触发 new AppClass(),并通过改实例调用 render()函数
ReactDOM.render(<AppClass/>, document.getElementById("app"))

```

### 2.4 props 相关属性

```jsx
class AppClass extends React.Component {
    //限制属性
    static propTypes = {
        name: PropTypes.string.isRequired,
        age: PropTypes.number.isRequired,
        say: PropTypes.func
    }
    //默认属性
    static defaultProps = {
        say() {
            console.log("ssss")
        }
    }

    render() {
        console.log(this)
        //props的属性是只读的，不能修改
        //this.props.sex = "MEN"
        const sex = this.props.sex
        const {say, name, age} = this.props
        say()
        return <div>
            <h1>测试props:{name}</h1>
            <h1>测试props:{age}</h1>
            <h1>测试props:{sex}</h1>
        </div>
    }

}

const person = {name: "hello", age: 15}

//babel和react实现对象展开运算符的语法，仅仅适用标签对象传递
ReactDOM.render(<AppClass {...person} sex='Vue'/>, document.getElementById("app"))
```

### 2.4 Ref

```jsx
class AppClass extends React.Component {

    showData = () => {
        console.log(this);
        console.log(this.rightInput)
    }

    myRef = React.createRef()
    showData2 = () => {
        console.log(this);
        //myRef实例
        console.log(this.myRef)
        //DOM标签
        console.log(this.myRef.current)
        //获取绑定myRef的标签的value
        console.log(this.myRef.current.value)
    }

    showData3 = (event) => {
        console.log(this);
        //事件Class
        console.log(event)
        //标签
        console.log(event.target)
    }

    render() {
        return (
            <div>
                /*字符串Ref*/
                <input ref="leftInput" type="text"/>
                <button onClick={this.showData}>点击我</button>
                //回调函数式Ref
                <input ref={c => this.rightInput = c} type="text"/>
                <button onClick={this.showData2}>点击我</button>
                //API方式
                <input ref={this.myRef} type="text"/>
                //避免使用 Ref
                <input onBlur={this.showData3} type="text"/>
            </div>
        )
    }

}

//babel和react实现对象展开运算符的语法，仅仅适用标签对象传递
ReactDOM.render(<AppClass/>, document.getElementById("app"))
```

### 2.5 form表单数据收集 username 和 password 属于受控组组件范畴，code数据非受控组件范畴

```jsx
    class Login extends React.Component {
    //通过onChange事件绑定表单数据到 state 里
    saveUsername = (event) => {
        this.setState({"username": event.target.value})
    }
    savePassword = (event) => {
        this.setState({"password": event.target.value})
    }
    handleLogin = (event) => {
        //阻止默认提交事件
        event.preventDefault();
        console.log(this)
        console.log(this.code.value)
        console.log(this.state)
    }

    //this.code 是通过ref的方式直接绑定到当前组件实例上的
    render() {
        return (
            <form action="https://react.docschina.org/docs/refs-and-the-dom.html" onSubmit={this.handleLogin}>
                <input onChange={this.saveUsername} type="text" name="username"/>
                <input onChange={this.savePassword} type="text" name="password"/>
                <input ref={c => this.code = c} type="text" name="code"/>
                <button type="submit"> 登录</button>
            </form>
        )
    }

}

ReactDOM.render(<Login/>, document.getElementById("app"))
```

### 2.6 高阶函数实现表单数据的收集方式
 
```jsx
//高阶函数
class Login extends React.Component {
    //通过onChange调用的式saveFormData返回的函数，所以event在回调函数里传入
    saveFormData = (dataName, event) => {
        this.setState({[dataName]: event.target.value})
    }
    handleLogin = (event) => {
        //阻止默认提交事件
        event.preventDefault();
        console.log(this)
        console.log(this.state)
    }

    render() {
        return (
            <form action="https://react.docschina.org/docs/refs-and-the-dom.html" onSubmit={this.handleLogin}>
                <input onChange={(event) => {
                    this.saveFormData("username", event)
                }} type="text" name="username"/>
                <input onChange={event => this.saveFormData("password", event)} type="text" name="password"/>
                <button type="submit"> 登录</button>
            </form>
        )
    }

}

ReactDOM.render(<Login/>, document.getElementById("app"))
```

```jsx
//非柯里化
class Login extends React.Component {
    saveFormData = (dataName) => {
        return (event) => {
            this.setState({[dataName]: event.target.value})
        }
    }
    handleLogin = (event) => {
        //阻止默认提交事件
        event.preventDefault();
        console.log(this)
        console.log(this.state)
    }

    render() {
        return (
            <form action="https://react.docschina.org/docs/refs-and-the-dom.html" onSubmit={this.handleLogin}>
                <input onChange={this.saveFormData("username")} type="text" name="username"/>
                <input onChange={this.saveFormData("password")} type="text" name="password"/>
                <button type="submit"> 登录</button>
            </form>
        )
    }
}

ReactDOM.render(<Login/>, document.getElementById("app"))

```

# 3、脚手架编写项目
### 3.1 todolist
### 3.2 ajax和proxy
### 3.3 pubsub
### 3.4 redux
