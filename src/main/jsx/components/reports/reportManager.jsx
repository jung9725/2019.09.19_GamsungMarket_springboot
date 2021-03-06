import React, {Component} from 'react';
import ReportManage from './reportManage.jsx'
import * as ManageService from '../../services/ManageService.js'

class ReportManager extends Component {
    constructor(props){
        super(props);
        this.state = {
            reports:[]
            
        }
    }

    getReportList(){
        const promise = ManageService.getReportList();
        promise.then(reports=>{
                    this.setState({
                        reports:reports
                    })
                })
                .catch(err=>{
                    console.log(err.message);
                })
    }

    componentDidMount(){
        this.getReportList();
    }

    render(){
        const {reports} = this.state;
        return(
            <div></div>

        )
    }
}
export default ReportManager;