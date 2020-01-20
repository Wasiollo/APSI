import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {TestService} from "../service/test.service";
import {Test} from "../model/test.model";

@Component({
    selector: 'app-tests-details',
    templateUrl: './tests-details.component.html',
    styleUrls: ['./tests-details.component.scss']
})
export class TestsDetailsComponent implements OnInit {

    currentTest: Test;
    testId: number;

    constructor(private router: Router, private testService: TestService) {
        this.testId = this.router.getCurrentNavigation().extras.state.testId;
    }

    ngOnInit() {
        this.currentTest = new Test();

        this.testService.getTest(this.testId).subscribe(data => {
            this.currentTest = data.result;
        })
    }

  returnToTestsList() {
    this.router.navigate(['tests'])
  }
}
