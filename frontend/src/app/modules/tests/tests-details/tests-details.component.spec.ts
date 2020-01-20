import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestsDetailsComponent } from './tests-details.component';

describe('TestsDetailsComponent', () => {
  let component: TestsDetailsComponent;
  let fixture: ComponentFixture<TestsDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestsDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestsDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
