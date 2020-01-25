import {Component, OnInit} from '@angular/core';
import {NavigationExtras, Router} from "@angular/router";
import {AuthenticationService} from "../../authentication/authentication.service";
import {Test} from "../model/test.model";
import {TestService} from "../service/test.service";
import {ToastrService} from "ngx-toastr";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {AddTestComponent} from "../add-test/add-test.component";
import {OK} from "http-status-codes";

@Component({
    selector: 'app-tests-list',
    templateUrl: './tests-list.component.html',
    styleUrls: ['./tests-list.component.scss']
})
export class TestsListComponent implements OnInit {

    tests: Test[];
    page: number;
    pageSize: number;
    testStatuses: string[];
    acceptedList: boolean;

    constructor(private router: Router, private testService: TestService, private authenticationService: AuthenticationService, private modalService: NgbModal, private toastr: ToastrService) {
    }

    ngOnInit() {
        this.acceptedList = true;
        this.tests = [];
        this.testStatuses = [];
        this.page = 1;
        this.pageSize = 9;
        if (this.authenticationService.hasRole((this.authenticationService.ROLE_TEST_LEADER))) {
            this.getAllTests();
        } else if (this.authenticationService.hasRole(this.authenticationService.ROLE_TESTER)) {
            this.getTests();
        } else {
            this.router.navigate(['home']);
        }
        this.getTestStatuses();

        this.testService.testAdded.subscribe(test => {
            if (!this.acceptedList) {
                this.tests.unshift(test);
            }
        });
    }

    getTests() {
        this.testService.getTests()
            .subscribe(data => {
                this.tests = data.result;
            });
    }

    private getTestStatuses() {
        this.testService.getTestStatuses()
            .subscribe(data => {
                this.testStatuses = data.result;
            });
    }

    getAllTests() {
        this.testService.getAllTests()
            .subscribe(data => {
                this.tests = data.result;
            });
    }

    showTestDetails(id: number) {
        const navigationExtras: NavigationExtras = {
            state: {
                testId: id
            }
        };
        this.router.navigate(['tests/test-details'], navigationExtras);
    }

    setTestStatus(id: number, testStatus: string) {
        // TODO set updateDate to now in view ? Reconsider (maybe only on changes in Details view)
        let testToChange = this.tests.find(t => t.id === id);
        testToChange.status = testStatus;
        this.testService.setTestStatus(testToChange).subscribe(data => {
            let testIndex = this.tests.findIndex(t => t.id === id);
            this.tests[testIndex] = data.result;
            this.toastr.success("Status changed to " + testStatus)
        });
    }

    addTest() {
        const modalRef = this.modalService.open(AddTestComponent);
        modalRef.componentInstance.modalRef = modalRef;
    }

    setAcceptedList(acc: boolean) {
        this.acceptedList = acc;
        if (acc) {
            this.getTests();
        } else {
            this.getUnacceptedTests();
        }
    }

    private getUnacceptedTests() {
        this.testService.getUnAcceptedTests()
            .subscribe(data => {
                this.tests = data.result;
            });
    }

    acceptTest(testId: number) {
        this.testService.acceptTest(testId).subscribe(data => {
            if (data.status === OK) {
                this.toastr.success("Test accepted successfully");
                this.tests = this.tests.filter(t => t.id != testId);
            } else {
                this.toastr.error("Error occurred when accepting");
            }
        });
    }

    deleteTest(testId: number): void {
        if (confirm('Are you sure to delete this test?')) {
            this.testService.deleteTest(testId)
                .subscribe( data => {
                    if(data.status === OK) {
                        this.tests = this.tests.filter(t => t.id !== testId);
                        this.toastr.success("Test deleted successfully");
                    }
                    else {
                        this.toastr.error("Error occurred when deleting");
                    }
                });
        }
    }
}
