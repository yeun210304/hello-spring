package hello.hellospring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping("/members/findOne")
    public String findOne() {
        return "members/searchMember";
    }

    @GetMapping("/members/searchMember")
    public ResponseEntity<?> searchMember(@RequestParam("id") String id, Model model) {
        Optional<Member> member = memberService.findOne(Long.parseLong(id));
        model.addAttribute("member", member.orElseGet(Member::new));
        return ResponseEntity.ok(member);
    }

    @PostMapping("/members/deleteMember")
    public String deleteMember(@RequestParam("id") Long id, Model model) {
        memberService.deleteMember(id);
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "redirect:/members";
    }

}
