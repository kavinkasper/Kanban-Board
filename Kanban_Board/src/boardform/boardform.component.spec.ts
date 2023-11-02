import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardformComponent } from './boardform.component';

describe('BoardformComponent', () => {
  let component: BoardformComponent;
  let fixture: ComponentFixture<BoardformComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BoardformComponent]
    });
    fixture = TestBed.createComponent(BoardformComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
